package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.rating.QuizRatingDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.rating.UserQuizRatingDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface QuizRatingApiService {

    @GET("/quizzes/rating")
    suspend fun readQuizRating(
        @Query("quizId") quizId: Long
    ): Response<QuizRatingDataEntity?>

    @GET("/quizzes/rating/ofUser")
    suspend fun readUserQuizRating(
        @Query("quizId") quizId: Long
    ): Response<UserQuizRatingDataEntity?>

    @POST("/quizzes/rating")
    suspend fun updateRating(
        @Query("quizId") quizId: Long,
        @Query("rating") rating: Int
    ): Response<Unit>
}




































