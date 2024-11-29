package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import retrofit2.http.GET

interface IAnswerRepository {

    @GET("/quizzes/questions/answers")
    suspend fun readAnswersByQuestionsId(questionId: Long): Answer<List<AnswerModel>>
}