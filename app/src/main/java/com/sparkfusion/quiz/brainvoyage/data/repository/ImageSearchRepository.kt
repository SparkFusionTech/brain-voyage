package com.sparkfusion.quiz.brainvoyage.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.sparkfusion.quiz.brainvoyage.data.datasource.image_search.ImageSearchApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.image_search.ImageSearchPagingDataSource
import com.sparkfusion.quiz.brainvoyage.data.mapper.image.ImageSearchDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.ImageSearchModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageSearchRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val service: ImageSearchApiService,
    private val imageSearchDataEntityFactory: ImageSearchDataEntityFactory
) : IImageSearchRepository {

    override suspend fun pagingReadImagesByQuery(query: String): Flow<PagingData<ImageSearchModel>> =
        withContext(ioDispatcher) {
            Pager(
                config = PagingConfig(
                    pageSize = ImageSearchApiService.PAGE_COUNT,
                    enablePlaceholders = false
                ),
                initialKey = ImageSearchApiService.INITIAL_PAGE,
                pagingSourceFactory = { ImageSearchPagingDataSource(service, ioDispatcher, query) }
            ).flow.map { pagingData ->
                pagingData.map { entity ->
                    imageSearchDataEntityFactory.mapTo(entity)
                }
            }
        }
}
