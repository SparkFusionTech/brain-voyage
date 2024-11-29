package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quiz_info

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ITagRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyQuizInfoViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository,
    private val tagRepository: ITagRepository,
    private val questionRepository: IQuestionRepository
) : MultiStateViewModel<MyQuizInfoContract.Intent>() {

    private val _quizReadingState = MutableStateFlow<MyQuizInfoContract.QuizReadingState>(
        MyQuizInfoContract.QuizReadingState.Loading
    )
    val quizReadingState: StateFlow<MyQuizInfoContract.QuizReadingState>
        get() = _quizReadingState.asStateFlow()

    private val _tagsReadingState =
        MutableStateFlow<MyQuizInfoContract.TagsReadingState>(MyQuizInfoContract.TagsReadingState.Empty)
    val tagsReadingState: StateFlow<MyQuizInfoContract.TagsReadingState>
        get() = _tagsReadingState.asStateFlow()

    private val _questionsReadingState =
        MutableStateFlow<MyQuizInfoContract.QuestionsReadingState>(MyQuizInfoContract.QuestionsReadingState.Empty)
    val questionsReadingState: StateFlow<MyQuizInfoContract.QuestionsReadingState>
        get() = _questionsReadingState.asStateFlow()

    override fun handleIntent(intent: MyQuizInfoContract.Intent) {
        when (intent) {
            is MyQuizInfoContract.Intent.ReadQuiz -> readQuiz(intent.quizId)
            is MyQuizInfoContract.Intent.ReadQuestions -> readQuestions(intent.quizId)
            is MyQuizInfoContract.Intent.ReadTags -> readTags(intent.quizId)
        }
    }

    private fun readQuestions(quizId: Long) {
        _questionsReadingState.update { MyQuizInfoContract.QuestionsReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            questionRepository.readQuestionsByQuizId(quizId)
                .onSuccess { questions ->
                    _questionsReadingState.update {
                        MyQuizInfoContract.QuestionsReadingState.Success(
                            questions
                        )
                    }
                }
                .onFailure {
                    _questionsReadingState.update { MyQuizInfoContract.QuestionsReadingState.Error }
                }
        }
    }

    private fun readTags(quizId: Long) {
        _tagsReadingState.update { MyQuizInfoContract.TagsReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            tagRepository.readTagsByQuizId(quizId)
                .onSuccess { tags ->
                    _tagsReadingState.update { MyQuizInfoContract.TagsReadingState.Success(tags) }
                }
                .onFailure {
                    _tagsReadingState.update { MyQuizInfoContract.TagsReadingState.Error }
                }
        }
    }

    private fun readQuiz(quizId: Long) {
        _quizReadingState.update { MyQuizInfoContract.QuizReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readSubmittedQuizById(quizId)
                .onSuccess { model ->
                    _quizReadingState.update { MyQuizInfoContract.QuizReadingState.Success(model) }
                }
                .onFailure {
                    _quizReadingState.update { MyQuizInfoContract.QuizReadingState.Error }
                }
        }
    }


}























