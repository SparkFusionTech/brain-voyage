package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection.OnlineQuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection.RANDOM_ICON_URL
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
class OnlineSelectionViewModel @Inject constructor(
    private val quizRepository: IQuizRepository
) : ViewModel() {

    private val _quizCatalogLoadingState =
        MutableStateFlow<QuizCatalogLoadingState>(QuizCatalogLoadingState.Loading)
    val quizCatalogLoadingState: StateFlow<QuizCatalogLoadingState> =
        _quizCatalogLoadingState.asStateFlow()

    fun updateSelectedItem(id: Long) {
        if (quizCatalogLoadingState.value is QuizCatalogLoadingState.Success) {
            val data = (quizCatalogLoadingState.value as QuizCatalogLoadingState.Success).data
                .map { model ->
                    if (model.id == id) model.copy(isSelected = true)
                    else model.copy(isSelected = false)
                }

            _quizCatalogLoadingState.update {
                QuizCatalogLoadingState.Success(data)
            }
        }
    }

    private fun readCatalog() {
        _quizCatalogLoadingState.update { QuizCatalogLoadingState.Loading }
        viewModelScope.launch {
            quizRepository.readCatalog()
                .onSuccess(::handleOnSuccessQuizCatalogLoading)
                .onFailure(::handleOnFailureQuizCatalogLoading)
        }
    }

    private fun handleOnSuccessQuizCatalogLoading(data: List<QuizCatalogModel>) {
        _quizCatalogLoadingState.update {
            val mutableData =
                data.map { OnlineQuizCatalogModel.mapToOnlineQuizCatalogModel(it) }.toMutableList()
            mutableData.add(
                0,
                OnlineQuizCatalogModel(
                    -1,
                    "Random",
                    RANDOM_ICON_URL,
                    "#FFFFFFFF",
                    "#FF808080",
                    true
                )
            )
            QuizCatalogLoadingState.Success(mutableData)
        }
    }

    private fun handleOnFailureQuizCatalogLoading(exception: BrainVoyageException) {
        _quizCatalogLoadingState.update {
            when (exception) {
                is NetworkException -> QuizCatalogLoadingState.NetworkError
                else -> QuizCatalogLoadingState.Error
            }
        }
    }

    init {
        readCatalog()
    }

    sealed interface QuizCatalogLoadingState {
        data object Loading : QuizCatalogLoadingState
        data object Error : QuizCatalogLoadingState
        data object NetworkError : QuizCatalogLoadingState
        data class Success(val data: List<OnlineQuizCatalogModel>) : QuizCatalogLoadingState
    }
}



























