package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.rating

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.rating.UserQuizRatingDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.UserQuizRatingModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class UserQuizRatingDataEntityFactory @Inject constructor(
) : MapFactory<UserQuizRatingDataEntity, UserQuizRatingModel> {

    override suspend fun mapFrom(input: UserQuizRatingModel): UserQuizRatingDataEntity {
        return UserQuizRatingDataEntity(input.id, input.rating)
    }

    override suspend fun mapTo(input: UserQuizRatingDataEntity): UserQuizRatingModel {
        return UserQuizRatingModel(input.id, input.rating)
    }
}