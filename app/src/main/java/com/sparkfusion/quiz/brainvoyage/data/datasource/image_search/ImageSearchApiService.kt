package com.sparkfusion.quiz.brainvoyage.data.datasource.image_search

import com.sparkfusion.quiz.brainvoyage.data.entity.ImageSearchDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageSearchApiService {

    @GET("/images/search")
    suspend fun pagingReadImagesByQuery(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<List<ImageSearchDataEntity>>

    companion object {
        const val INITIAL_PAGE = 1
        const val PAGE_COUNT = 50
    }
}
