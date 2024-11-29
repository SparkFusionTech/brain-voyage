package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AnswerDataEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AnswerApiService {

    @GET("/quizzes/questions/answers")
    suspend fun readAnswerByQuestionId(
        @Query("questionId") questionId: Long
    ): Response<List<AnswerDataEntity>>
}
