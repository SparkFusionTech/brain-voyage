package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ITagRepository
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.image.ApiImageSerializeNames
import com.sparkfusion.quiz.brainvoyage.utils.image.BitmapToFileWorker
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageChildren
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageFileToMultipartWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizWithQuestionsViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository,
    private val tagRepository: ITagRepository,
    private val questionRepository: IQuestionRepository,
    private val bitmapToFileWorker: BitmapToFileWorker,
    private val imageFileToMultipartWorker: ImageFileToMultipartWorker
) : MultiStateViewModel<Intent>() {

    private val _state = MutableStateFlow(AddQuizWithQuestionsContract.State())
    val state: StateFlow<AddQuizWithQuestionsContract.State> get() = _state.asStateFlow()

    private val _quizVerificationState =
        MutableStateFlow<AddQuizWithQuestionsContract.QuizVerificationState>(
            AddQuizWithQuestionsContract.QuizVerificationState.Empty
        )
    val quizVerificationState: StateFlow<AddQuizWithQuestionsContract.QuizVerificationState>
        get() = _quizVerificationState.asStateFlow()

    private val _quizAddingState = MutableStateFlow<AddQuizWithQuestionsContract.QuizSavingState>(
        AddQuizWithQuestionsContract.QuizSavingState.Empty
    )
    val quizAddingState: StateFlow<AddQuizWithQuestionsContract.QuizSavingState>
        get() = _quizAddingState.asStateFlow()

    override fun handleIntent(intent: Intent) {
        when (intent) {
            is Intent.ChangeCloseDialogVisibility -> changeCloseDialogVisibility(
                intent.value
            )

            is Intent.SaveQuiz -> saveQuiz(
                intent.addQuizInitialModel, intent.questions
            )

            Intent.ClearQuizVerificationState -> clearPublishState()
            is Intent.ChangePublicationDialogVisibility -> changePublicationDialogVisibility(intent.value)
            Intent.ClearSavingState -> clearSavingState()
        }
    }

    private fun clearSavingState() {
        _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.Empty }
    }

    private fun changePublicationDialogVisibility(value: Boolean) {
        _state.update { it.copy(showPublicationDialog = value) }
    }

    private fun clearPublishState() {
        _quizVerificationState.update { AddQuizWithQuestionsContract.QuizVerificationState.Empty }
    }

    private fun saveQuiz(
        addQuizInitialModel: AddQuizInitialModel?,
        questions: List<SendQuestionModel>
    ) {
        if (!checkIsSavingReady(addQuizInitialModel, questions.size)) return
        _state.update { it.copy(showPublicationDialog = true) }

        viewModelScope.launch(ioDispatcher) {
            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.QuizSaving }
            val quizSaveAnswer = quizRepository.createQuiz(
                addQuizInitialModel!!.map(questions.size),
                imageFileToMultipartWorker.invoke(
                    bitmapToFileWorker.invoke(addQuizInitialModel.bitmap, ImageChildren.QUIZ_ICON),
                    ApiImageSerializeNames.QUIZ_ICON.value
                )
            ).onFailure {
                _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.QuizSavingError }
                return@launch
            }

            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.TagsSaving }
            tagRepository.createTags(addQuizInitialModel.tags, quizSaveAnswer.unwrap().id)
                .onFailure {
                    _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.TagsSavingError }
                    return@launch
                }

            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.QuestionsSaving }
            questions.forEach {
                questionRepository.createQuestion(
                    it.map(quizSaveAnswer.unwrap().id),
                    imageFileToMultipartWorker.invoke(
                        bitmapToFileWorker.invoke(it.icon, ImageChildren.QUESTION_ICON),
                        ApiImageSerializeNames.QUESTION_ICON.value
                    )
                ).onFailure {
                    AddQuizWithQuestionsContract.QuizSavingState.QuestionsSavingError
                }
            }

            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.Success }
        }
    }

    private fun checkIsSavingReady(
        addQuizInitialModel: AddQuizInitialModel?,
        questionsCount: Int
    ): Boolean {
        if (addQuizInitialModel == null) {
            _quizVerificationState.update { AddQuizWithQuestionsContract.QuizVerificationState.QuizVerificationIsNull }
            return false
        }

        if (questionsCount < 5) {
            _quizVerificationState.update { AddQuizWithQuestionsContract.QuizVerificationState.NotEnoughQuestions }
            return false
        }

        return true
    }

    private fun changeCloseDialogVisibility(value: Boolean) {
        _state.update { it.copy(showCloseDialog = value) }
    }
}




















