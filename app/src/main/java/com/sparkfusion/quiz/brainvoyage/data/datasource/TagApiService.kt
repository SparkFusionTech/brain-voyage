package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.tags.TagsRequestDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TagApiService {

    @POST("/quizzes/tags/createAll")
    suspend fun createTags(
        @Body tagsRequest: TagsRequestDataEntity
    ): Response<Int>
}

























