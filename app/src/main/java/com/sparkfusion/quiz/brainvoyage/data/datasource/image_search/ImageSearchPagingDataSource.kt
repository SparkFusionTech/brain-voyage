package com.sparkfusion.quiz.brainvoyage.data.datasource.image_search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.common.safePagingApiCall
import com.sparkfusion.quiz.brainvoyage.data.entity.ImageSearchDataEntity
import kotlinx.coroutines.CoroutineDispatcher

class ImageSearchPagingDataSource(
    private val imageSearchApiService: ImageSearchApiService,
    private val dispatcher: CoroutineDispatcher,
    private val query: String
) : PagingSource<Int, ImageSearchDataEntity>() {

    override fun getRefreshKey(state: PagingState<Int, ImageSearchDataEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageSearchDataEntity> {
        val currentPage = params.key ?: ImageSearchApiService.INITIAL_PAGE
        val perPage = params.loadSize
        return safePagingApiCall(dispatcher) {
                val response = imageSearchApiService.pagingReadImagesByQuery(query, currentPage, perPage)
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    LoadResult.Page(
                        data = data,
                        prevKey = if (currentPage == ImageSearchApiService.INITIAL_PAGE) null else currentPage - 1,
                        nextKey = if (data.isEmpty()) null else currentPage + 1
                    )
                } else {
                    LoadResult.Error(handleExceptionCode(response.code()))
                }
        }
    }
}
