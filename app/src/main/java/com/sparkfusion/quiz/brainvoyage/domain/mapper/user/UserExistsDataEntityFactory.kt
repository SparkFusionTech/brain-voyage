package com.sparkfusion.quiz.brainvoyage.domain.mapper.user

import com.sparkfusion.quiz.brainvoyage.data.entity.UserExistsDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.UserExistsModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class UserExistsDataEntityFactory @Inject constructor(): MapFactory<UserExistsDataEntity, UserExistsModel> {

    override suspend fun mapTo(input: UserExistsDataEntity): UserExistsModel {
        return UserExistsModel(input.exists)
    }

    override suspend fun mapFrom(input: UserExistsModel): UserExistsDataEntity {
        return UserExistsDataEntity(input.exists)
    }
}
