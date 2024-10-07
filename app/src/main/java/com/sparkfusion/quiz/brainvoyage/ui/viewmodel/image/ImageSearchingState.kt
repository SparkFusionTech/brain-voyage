package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image

import androidx.paging.PagingData
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel
import kotlinx.coroutines.flow.Flow

sealed interface ImageSearchingState {
    data object Empty : ImageSearchingState
    data object Loading : ImageSearchingState
    data class PagingSuccess(val data: Flow<PagingData<ImageSearchModel>>) : ImageSearchingState
}
