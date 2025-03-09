package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizCatalogViewModel @Inject constructor(
    private val quizRepository: IQuizRepository
) : MultiStateViewModel<QuizCatalogContract.QuizCatalogIntent>() {

    private val _readingState =
        MutableStateFlow<QuizCatalogLoadingState>(QuizCatalogLoadingState.Initial)
    val readingState: StateFlow<QuizCatalogLoadingState> = _readingState.asStateFlow()

    override fun handleIntent(intent: QuizCatalogContract.QuizCatalogIntent) {
        when (intent) {
            QuizCatalogContract.QuizCatalogIntent.LoadQuizCatalog -> loadQuizCatalog()
            QuizCatalogContract.QuizCatalogIntent.ClearReadingState -> clearReadingState()
        }
    }

    private fun loadQuizCatalog() {
        if (readingState.value is QuizCatalogLoadingState.Success) return
        if (readingState.value == QuizCatalogLoadingState.Loading) return

        _readingState.update { QuizCatalogLoadingState.Loading }
        viewModelScope.launch {
            quizRepository.readCatalog()
                .onSuccess(::handleOnSuccessQuizCatalogLoading)
                .onFailure(::handleOnFailureQuizCatalogLoading)
        }
    }

    private fun clearReadingState() {
        _readingState.update { QuizCatalogLoadingState.Initial }
    }

    private fun handleOnSuccessQuizCatalogLoading(data: List<QuizCatalogModel>) {
        _readingState.update { QuizCatalogLoadingState.Success(data) }
    }

    private fun handleOnFailureQuizCatalogLoading(exception: BrainVoyageException) {
        _readingState.update {
            when (exception) {
                is NetworkException -> QuizCatalogLoadingState.NetworkError
                else -> QuizCatalogLoadingState.Error
            }
        }
    }
}

















