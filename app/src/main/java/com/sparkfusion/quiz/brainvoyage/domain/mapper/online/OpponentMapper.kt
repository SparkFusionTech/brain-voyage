package com.sparkfusion.quiz.brainvoyage.domain.mapper.online

import com.sparkfusion.quiz.brainvoyage.data.entity.online.OpponentEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OpponentModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class OpponentMapper @Inject constructor(
): MapFactory<OpponentEntity, OpponentModel> {

    override suspend fun mapTo(input: OpponentEntity): OpponentModel = with(input) {
        OpponentModel(id, name, iconUrl)
    }

    override suspend fun mapFrom(input: OpponentModel): OpponentEntity = with(input) {
        OpponentEntity(id, name, iconUrl)
    }
}
























