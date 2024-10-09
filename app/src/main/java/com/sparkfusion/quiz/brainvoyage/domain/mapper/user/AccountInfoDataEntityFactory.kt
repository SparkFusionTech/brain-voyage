package com.sparkfusion.quiz.brainvoyage.domain.mapper.user

import com.sparkfusion.quiz.brainvoyage.data.entity.AccountInfoDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.AccountInfoModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AccountInfoDataEntityFactory @Inject constructor() :
    MapFactory<AccountInfoDataEntity, AccountInfoModel> {

    override suspend fun mapFrom(input: AccountInfoModel): AccountInfoDataEntity = with(input) {
        AccountInfoDataEntity(name, iconUrl)
    }

    override suspend fun mapTo(input: AccountInfoDataEntity): AccountInfoModel = with(input) {
        AccountInfoModel(name, iconUrl)
    }
}
