package com.sparkfusion.quiz.brainvoyage.data.mapper

import com.sparkfusion.quiz.brainvoyage.data.entity.TokenDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.TokenModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class TokenDataEntityFactory @Inject constructor() : MapFactory<TokenDataEntity, TokenModel> {

    override suspend fun mapTo(input: TokenDataEntity): TokenModel {
        return TokenModel(input.token)
    }

    override suspend fun mapFrom(input: TokenModel): TokenDataEntity {
        return TokenDataEntity(input.token)
    }
}