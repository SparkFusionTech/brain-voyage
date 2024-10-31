package com.sparkfusion.quiz.brainvoyage.data.datasource

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface TagApiService {

    @POST("/quizzes/tags/createAll")
    suspend fun createTags(
        @Query("tags") tags: List<String>,
        @Query("quizId") quizId: Long
    ): Response<Int>
}
























