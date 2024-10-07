package com.sparkfusion.quiz.brainvoyage.domain.repository

import androidx.paging.PagingData
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel
import kotlinx.coroutines.flow.Flow

interface IImageSearchRepository {

    suspend fun pagingReadImagesByQuery(query: String): Flow<PagingData<ImageSearchModel>>
}
