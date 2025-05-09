package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import okhttp3.MultipartBody

interface IQuestionRepository {

    suspend fun createQuestion(
        addQuestion: AddQuestionModel,
        questionImage: MultipartBody.Part
    ): Answer<Unit>

    suspend fun readQuestionsByQuizId(quizId: Long): Answer<List<QuestionModel>>

    suspend fun readQuestionsWithAnswersByQuizId(quizId: Long): Answer<List<QuestionWithAnswersModel>>
}
