package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizCatalogViewModel @Inject constructor(
    private val quizRepository: IQuizRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CommonViewModel<QuizCatalogContract.QuizCatalogState, QuizCatalogContract.QuizCatalogIntent>() {

    override fun initialState(): QuizCatalogContract.QuizCatalogState = state

    override fun handleIntent(intent: QuizCatalogContract.QuizCatalogIntent) {
        when (intent) {
            QuizCatalogContract.QuizCatalogIntent.LoadQuizCatalog -> loadQuizCatalog()
        }
    }

    private var state: QuizCatalogContract.QuizCatalogState by mutableStateOf(QuizCatalogContract.QuizCatalogState())

    private fun loadQuizCatalog() {
        state = state.copy(catalogLoadingState = QuizCatalogLoadingState.Loading)
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readCatalog()
                .onSuccess(::handleOnSuccessQuizCatalogLoading)
                .onFailure(::handleOnFailureQuizCatalogLoading)
        }
    }

    private fun handleOnSuccessQuizCatalogLoading(data: List<QuizCatalogModel>) {
        state = state.copy(catalogLoadingState = QuizCatalogLoadingState.Success(data))
    }

    private fun handleOnFailureQuizCatalogLoading(exception: BrainVoyageException) {
        state = state.copy(
            catalogLoadingState = when (exception) {
                is NetworkException -> QuizCatalogLoadingState.NetworkError
                else -> QuizCatalogLoadingState.Error
            }
        )
    }

    init {
        loadQuizCatalog()
    }
}
