package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagsRequestDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TagApiService {

    @POST("/quizzes/tags/createAll")
    suspend fun createTags(
        @Body tagsRequest: TagsRequestDataEntity
    ): Response<Int>

    @GET("/quizzes/tags")
    suspend fun readTagsByQuizId(
        @Query("quizId") quizId: Long
    ): Response<List<TagDataEntity>>
}

























