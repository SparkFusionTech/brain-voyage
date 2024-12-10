package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionWithAnswersDataEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface QuestionApiService {

    @Multipart
    @POST("/quizzes/questions/create")
    suspend fun createQuestion(
        @Part("addQuestionDto") addQuestion: RequestBody,
        @Part questionImage: MultipartBody.Part
    ): Response<Unit>

    @GET("/quizzes/questions")
    suspend fun readQuestionsByQuizId(
        @Query("quizId") quizId: Long
    ): Response<List<QuestionDataEntity>>

    @GET("/quizzes/questions/play")
    suspend fun readQuestionsWithAnswers(
        @Query("quizId") quizId: Long
    ): Response<List<QuestionWithAnswersDataEntity>>
}
