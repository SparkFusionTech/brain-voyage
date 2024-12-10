package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play

import android.util.Log
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.PlayAnswerModel
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.domain.play_worker.XpCounter
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.model.AnsweredQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizContract
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayQuizViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val answeredQuestionFactory: AnsweredQuestionFactory,
    private val questionRepository: IQuestionRepository,
    private val xpCounter: XpCounter
) : MultiStateViewModel<PlayQuizContract.PlayQuizIntent>() {

    private val coroutineScope = CoroutineScope(ioDispatcher + SupervisorJob())

    private val questions = mutableListOf<QuestionWithAnswersModel>()
    private val answeredQuestions = mutableListOf<AnsweredQuestionModel>()

    private val _currentQuestion =
        MutableStateFlow<PlayQuizContract.CurrentQuestionState>(PlayQuizContract.CurrentQuestionState.Loading)
    val currentQuestion: StateFlow<PlayQuizContract.CurrentQuestionState> get() = _currentQuestion.asStateFlow()

    private val _answerResult =
        MutableStateFlow<PlayQuizContract.AnswerCheckState>(PlayQuizContract.AnswerCheckState.Empty)
    val answerResult: StateFlow<PlayQuizContract.AnswerCheckState> get() = _answerResult.asStateFlow()

    private val _errorState = MutableStateFlow(PlayQuizContract.ErrorState())
    val errorState: StateFlow<PlayQuizContract.ErrorState> get() = _errorState.asStateFlow()

    private val _completedValueState = MutableStateFlow(PlayQuizContract.CompletedValueState())
    val completedValueState: StateFlow<PlayQuizContract.CompletedValueState> get() = _completedValueState.asStateFlow()

    private val _dialogsState = MutableStateFlow(PlayQuizContract.DialogsState())
    val dialogsState: StateFlow<PlayQuizContract.DialogsState> get() = _dialogsState.asStateFlow()

    override fun handleIntent(intent: PlayQuizContract.PlayQuizIntent) {
        when (intent) {
            is PlayQuizContract.PlayQuizIntent.ReadQuestions -> loadQuestions(intent.quizId)
            is PlayQuizContract.PlayQuizIntent.SelectAnswer -> selectAnswer(intent.answerIdInList)
            is PlayQuizContract.PlayQuizIntent.NextQuestion -> handleNextQuestion(intent.quizId)
            PlayQuizContract.PlayQuizIntent.CheckAnswers -> handleQuestionAnswer()
            is PlayQuizContract.PlayQuizIntent.ChangeExitDialogVisibility -> changeExitDialogVisibility(intent.isVisible)
        }
    }

    private fun changeExitDialogVisibility(visible: Boolean) {
        _dialogsState.update { it.copy(isCloseDialogVisible = visible) }
    }

    private fun selectAnswer(answerIdInList: Int) {
        val currentQuestionState = currentQuestion.value
        if (currentQuestionState is PlayQuizContract.CurrentQuestionState.CurrentQuestion) {
            val question = currentQuestionState.question
            val category = CategoryType.fromInt(question.category)
            if (answerIdInList in question.answers.indices) {
                val updatedAnswers = updateAnswers(question.answers, answerIdInList, category)
                val updatedQuestion = question.copy(answers = updatedAnswers)
                _currentQuestion.update {
                    PlayQuizContract.CurrentQuestionState.CurrentQuestion(updatedQuestion)
                }
            }
        }
    }

    private fun updateAnswers(
        answers: List<PlayAnswerModel>,
        selectedAnswerIndex: Int,
        category: CategoryType?
    ): List<PlayAnswerModel> {
        return answers.mapIndexed { index, answer ->
            when {
                category == CategoryType.MultiplyChoice && index == selectedAnswerIndex -> answer.copy(
                    isSelected = !answer.isSelected
                )

                category != CategoryType.MultiplyChoice && index == selectedAnswerIndex -> answer.copy(
                    isSelected = true
                )

                else -> answer.copy(
                    isSelected = false
                )
            }
        }
    }

    private fun loadQuestions(quizId: Long) {
        if (questions.isNotEmpty()) return
        coroutineScope.launch {
            questionRepository.readQuestionsWithAnswersByQuizId(quizId)
                .onSuccess {
                    questions.addAll(it.shuffled())
                    setFirstQuestion(questions[0])
                }
                .onFailure {
                    Log.i("TAGTAG", it.message.toString())
                }
        }
    }

    private fun setFirstQuestion(firstQuestion: QuestionWithAnswersModel) {
        _currentQuestion.update {
            PlayQuizContract.CurrentQuestionState.CurrentQuestion(
                firstQuestion.copy(answers = firstQuestion.answers.shuffled())
            )
        }
    }

    private fun handleQuestionAnswer() {
        coroutineScope.launch(defaultDispatcher) {
            if (currentQuestion.value is PlayQuizContract.CurrentQuestionState.CurrentQuestion) {
                val currentQuestion =
                    currentQuestion.value as PlayQuizContract.CurrentQuestionState.CurrentQuestion
                val userAnswersIds = currentQuestion.question.answers.asSequence()
                    .filter { it.isSelected }
                    .map { it.id }
                    .toList()

                if (userAnswersIds.isEmpty()) {
                    _errorState.update { it.copy(isAnswerNotSelected = true) }
                    return@launch
                }

                val currentCorrectAnswers = currentQuestion.question.answers.asSequence()
                    .filter { it.correct }
                    .map { it.id }
                    .toList()

                val isCorrect = userAnswersIds.all { it in currentCorrectAnswers }
                val incorrectAnswers = userAnswersIds.filterNot { it in currentCorrectAnswers }

                answeredQuestions.add(
                    answeredQuestionFactory.mapTo(
                        currentQuestion.question,
                        isCorrect
                    )
                )
                _completedValueState.update {
                    PlayQuizContract.CompletedValueState(answeredQuestions.size.toFloat() / questions.size * 100)
                }
                _answerResult.update {
                    PlayQuizContract.AnswerCheckState.AnswerCheckResult(
                        correctAnswersIds = currentCorrectAnswers,
                        incorrectAnswersIds = incorrectAnswers,
                        isCorrect = isCorrect
                    )
                }
            }
        }
    }

    private fun handleNextQuestion(quizId: Long) {
        val currentVisibleQuestion = currentQuestion.value
        if (currentVisibleQuestion is PlayQuizContract.CurrentQuestionState.CurrentQuestion) {
            if (questions.size == answeredQuestions.size) {
                _currentQuestion.update {
                    PlayQuizContract.CurrentQuestionState.Victory(
                        VictoryQuizContract.InitialState(
                            quizId = quizId,
                            questionsCount = questions.size,
                            correctAnswersCount = answeredQuestions.filter { it.isCorrectAnswer }.size,
                            xpCount = answeredQuestions.sumOf {
                                if (it.isCorrectAnswer) xpCounter.countXpForQuestion(it.difficulty) else 0
                            }
                        )
                    )
                }
                return
            }

            val searchableQuestion = questions.find { it.id == currentVisibleQuestion.question.id }
            val currentQuestionIdInList = questions.indexOf(searchableQuestion)
            val nextQuestion = questions[currentQuestionIdInList + 1]

            _answerResult.update { PlayQuizContract.AnswerCheckState.Empty }
            _currentQuestion.update {
                PlayQuizContract.CurrentQuestionState.CurrentQuestion(
                    nextQuestion.copy(answers = nextQuestion.answers.shuffled())
                )
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}























