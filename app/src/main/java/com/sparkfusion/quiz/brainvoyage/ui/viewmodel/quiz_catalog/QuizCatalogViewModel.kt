package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.SingleStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizCatalogViewModel @Inject constructor(
    private val quizRepository: IQuizRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : SingleStateViewModel<QuizCatalogContract.QuizCatalogState, QuizCatalogContract.QuizCatalogIntent>() {

    override fun initialState(): StateFlow<QuizCatalogContract.QuizCatalogState> = state.asStateFlow()

    override fun handleIntent(intent: QuizCatalogContract.QuizCatalogIntent) {
        when (intent) {
            QuizCatalogContract.QuizCatalogIntent.LoadQuizCatalog -> loadQuizCatalog()
        }
    }

    private val state = MutableStateFlow(QuizCatalogContract.QuizCatalogState())

    private fun loadQuizCatalog() {
        state.update { it.copy(catalogLoadingState = QuizCatalogLoadingState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readCatalog()
                .onSuccess(::handleOnSuccessQuizCatalogLoading)
                .onFailure(::handleOnFailureQuizCatalogLoading)
        }
    }

    private fun handleOnSuccessQuizCatalogLoading(data: List<QuizCatalogModel>) {
        state.update { it.copy(catalogLoadingState = QuizCatalogLoadingState.Success(data)) }
    }

    private fun handleOnFailureQuizCatalogLoading(exception: BrainVoyageException) {
        state.update {
            it.copy(
                catalogLoadingState = when (exception) {
                    is NetworkException -> QuizCatalogLoadingState.NetworkError
                    else -> QuizCatalogLoadingState.Error
                }
            )
        }
    }

    init {
        loadQuizCatalog()
    }
}
