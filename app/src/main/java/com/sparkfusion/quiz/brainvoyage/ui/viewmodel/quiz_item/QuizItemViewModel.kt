package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_item

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
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
class QuizItemViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository
) : MultiStateViewModel<QuizItemContract.Intent>() {

    private val _quizLoadingState =
        MutableStateFlow<QuizItemContract.QuizReadingState>(QuizItemContract.QuizReadingState.Loading)
    val quizLoadingState: StateFlow<QuizItemContract.QuizReadingState>
        get() = _quizLoadingState.asStateFlow()

    override fun handleIntent(intent: QuizItemContract.Intent) {
        when (intent) {
            is QuizItemContract.Intent.ReadQuiz -> readQuiz(intent.quizId)
        }
    }

    private fun readQuiz(quizId: Long) {
        _quizLoadingState.update { QuizItemContract.QuizReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readQuizById(quizId)
                .onSuccess { model ->
                    _quizLoadingState.update {
                        QuizItemContract.QuizReadingState.Success(model)
                    }
                }
                .onFailure {
                    _quizLoadingState.update { QuizItemContract.QuizReadingState.Error }
                }
        }
    }
}























