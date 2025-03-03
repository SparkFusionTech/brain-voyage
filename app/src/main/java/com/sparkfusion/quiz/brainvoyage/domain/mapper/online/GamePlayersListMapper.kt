package com.sparkfusion.quiz.brainvoyage.domain.mapper.online

import com.sparkfusion.quiz.brainvoyage.data.entity.online.GamePlayersListEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.online.GamePlayersListModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class GamePlayersListMapper @Inject constructor(
    private val playerMapper: PlayerMapper
) : MapFactory<GamePlayersListEntity, GamePlayersListModel> {

    override suspend fun mapFrom(input: GamePlayersListModel): GamePlayersListEntity {
        return GamePlayersListEntity(input.players.map { playerMapper.mapFrom(it) })
    }

    override suspend fun mapTo(input: GamePlayersListEntity): GamePlayersListModel {
        return GamePlayersListModel(input.players.map { playerMapper.mapTo(it) })
    }
}