package com.sparkfusion.quiz.brainvoyage.domain.mapper.online

import com.sparkfusion.quiz.brainvoyage.data.entity.online.PlayerEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.online.PlayerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class PlayerMapper @Inject constructor(
) : MapFactory<PlayerEntity, PlayerModel> {

    override suspend fun mapFrom(input: PlayerModel): PlayerEntity = with(input) {
        PlayerEntity(id, name, iconUrl, score)
    }

    override suspend fun mapTo(input: PlayerEntity): PlayerModel = with(input) {
        PlayerModel(id, name, iconUrl, score)
    }
}