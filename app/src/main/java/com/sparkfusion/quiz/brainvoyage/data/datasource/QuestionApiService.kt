package com.sparkfusion.quiz.brainvoyage.data.datasource

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface QuestionApiService {

    @Multipart
    @POST("/quizzes/questions/create")
    suspend fun createQuestion(
        @Part("addQuestionDto") addQuestion: RequestBody,
        @Part questionImage: MultipartBody.Part
    ): Response<Unit>
}
