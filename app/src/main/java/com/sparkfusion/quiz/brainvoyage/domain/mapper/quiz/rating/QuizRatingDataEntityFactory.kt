package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.rating

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.rating.QuizRatingDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuizRatingDataEntityFactory @Inject constructor(
): MapFactory<QuizRatingDataEntity, QuizRatingModel> {

    override suspend fun mapFrom(input: QuizRatingModel): QuizRatingDataEntity {
        return QuizRatingDataEntity(input.rating)
    }

    override suspend fun mapTo(input: QuizRatingDataEntity): QuizRatingModel {
        return QuizRatingModel(input.rating)
    }
}