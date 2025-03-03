package com.sparkfusion.quiz.brainvoyage.domain.mapper.online

import com.sparkfusion.quiz.brainvoyage.data.entity.online.RoomIdEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.online.RoomIdModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class RoomIdEntityMapper @Inject constructor(
): MapFactory<RoomIdEntity, RoomIdModel> {

    override suspend fun mapFrom(input: RoomIdModel): RoomIdEntity {
        return RoomIdEntity(input.roomId)
    }

    override suspend fun mapTo(input: RoomIdEntity): RoomIdModel {
        return RoomIdModel(input.roomId)
    }
}