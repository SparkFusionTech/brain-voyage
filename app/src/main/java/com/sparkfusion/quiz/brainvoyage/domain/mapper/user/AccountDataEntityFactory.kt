package com.sparkfusion.quiz.brainvoyage.domain.mapper.user

import com.sparkfusion.quiz.brainvoyage.data.entity.AccountDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.AccountModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AccountDataEntityFactory @Inject constructor(
): MapFactory<AccountDataEntity, AccountModel> {

    override suspend fun mapFrom(input: AccountModel): AccountDataEntity = with(input) {
        AccountDataEntity(id, email, password, iconUrl, name, createdAt)
    }

    override suspend fun mapTo(input: AccountDataEntity): AccountModel = with(input) {
        AccountModel(id, email, password, iconUrl, name, createdAt)
    }
}