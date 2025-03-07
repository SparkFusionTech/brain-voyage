package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.UserQuizRatingModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer

interface IQuizRatingRepository {

    suspend fun readQuizRating(quizId: Long): Answer<QuizRatingModel?>

    suspend fun readUserQuizRating(quizId: Long): Answer<UserQuizRatingModel?>

    suspend fun updateRating(quizId: Long, rating: Int): Answer<Unit>
}