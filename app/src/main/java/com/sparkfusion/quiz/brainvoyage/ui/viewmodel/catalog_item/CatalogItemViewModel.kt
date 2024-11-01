package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
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
class CatalogItemViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository
) : MultiStateViewModel<CatalogItemContract.Intent>() {

    private val _quizLoadingState =
        MutableStateFlow<CatalogItemContract.QuizLoadingState>(CatalogItemContract.QuizLoadingState.Loading)
    val quizLoadingState: StateFlow<CatalogItemContract.QuizLoadingState>
        get() = _quizLoadingState.asStateFlow()

    override fun handleIntent(intent: CatalogItemContract.Intent) {
        when (intent) {
            is CatalogItemContract.Intent.LoadQuizzes -> loadQuizzes(intent.catalogId)
        }
    }

    private fun loadQuizzes(catalogId: Long) {
        _quizLoadingState.update { CatalogItemContract.QuizLoadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readQuizzesByCatalogId(catalogId)
                .onSuccess { quizzes ->
                    _quizLoadingState.update { CatalogItemContract.QuizLoadingState.Success(quizzes) }
                }
                .onFailure { exception ->
                    _quizLoadingState.update {
                        when (exception) {
                            is NetworkException -> CatalogItemContract.QuizLoadingState.NetworkError
                            else -> CatalogItemContract.QuizLoadingState.Error
                        }
                    }
                }
        }
    }
}




















