package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import okhttp3.MultipartBody

interface IQuestionRepository {

    suspend fun createQuestion(
        addQuestion: AddQuestionModel,
        questionImage: MultipartBody.Part
    ): Answer<Unit>
}
