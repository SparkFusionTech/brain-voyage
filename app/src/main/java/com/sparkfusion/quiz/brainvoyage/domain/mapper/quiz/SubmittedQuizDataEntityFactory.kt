package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.SubmittedQuizDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.QuizStatusModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class SubmittedQuizDataEntityFactory @Inject constructor(
) : MapFactory<SubmittedQuizDataEntity, SubmittedQuizModel> {

    override suspend fun mapTo(input: SubmittedQuizDataEntity): SubmittedQuizModel = with(input) {
        SubmittedQuizModel(id, title, description, QuizStatusModel.fromValue(status), rating, imageUrl, questions, createdAt)
    }

    override suspend fun mapFrom(input: SubmittedQuizModel): SubmittedQuizDataEntity = with(input) {
        SubmittedQuizDataEntity(id, title, description, status.value, rating, imageUrl, questions, createdAt)
    }
}