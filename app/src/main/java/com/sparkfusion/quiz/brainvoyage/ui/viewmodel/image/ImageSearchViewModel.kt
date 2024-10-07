package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sparkfusion.quiz.brainvoyage.domain.repository.IImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
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
class ImageSearchViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: IImageSearchRepository
) : CommonViewModel<ImageSearchContract.ImageSearchState, ImageSearchContract.ImageSearchIntent>() {

    override fun initialState(): StateFlow<ImageSearchContract.ImageSearchState> = state.asStateFlow()

    override fun handleIntent(intent: ImageSearchContract.ImageSearchIntent) {
        when (intent) {
            ImageSearchContract.ImageSearchIntent.LoadImages -> reloadImages()
            is ImageSearchContract.ImageSearchIntent.ChangeQuery -> changeQuery(intent.value)
            is ImageSearchContract.ImageSearchIntent.HandleErrorLoading -> handleErrorLoading(intent.error)
        }
    }

    private val state = MutableStateFlow(ImageSearchContract.ImageSearchState())

    private fun loadRecordsByQuery() {
        state.update { it.copy(imageSearchingState = ImageSearchingState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            state.update {
                it.copy(imageSearchingState = ImageSearchingState.PagingSuccess(
                    repository.pagingReadImagesByQuery(state.value.query)
                        .cachedIn(viewModelScope)
                ))
            }
        }
    }

    private fun reloadImages() {
        if (state.value.query.isEmpty()) {
            state.update { it.copy(isQueryEmpty = true) }
        } else {
            loadRecordsByQuery()
        }
    }

    private fun handleErrorLoading(error: Throwable) {

    }

    private fun changeQuery(newValue: String) {
        state.update { it.copy(query = newValue) }
    }
}
