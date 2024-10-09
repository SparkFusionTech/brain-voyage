package com.sparkfusion.quiz.brainvoyage.domain.mapper.user

import com.sparkfusion.quiz.brainvoyage.data.entity.LoginUserDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class LoginUserDataEntityFactory @Inject constructor() : MapFactory<LoginUserDataEntity, LoginUserModel> {

    override suspend fun mapTo(input: LoginUserDataEntity): LoginUserModel = with(input) {
        LoginUserModel(email, password)
    }

    override suspend fun mapFrom(input: LoginUserModel): LoginUserDataEntity = with(input) {
        LoginUserDataEntity(email, password)
    }
}