package com.sparkfusion.quiz.brainvoyage.domain.mapper.online

import com.sparkfusion.quiz.brainvoyage.data.entity.online.GameEvent
import com.sparkfusion.quiz.brainvoyage.domain.mapper.question.QuestionWithAnswersDataEntityMapper
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map

fun SharedFlow<GameEvent>.map(
    roomIdEntityMapper: RoomIdEntityMapper,
    questionWithAnswersDataEntityMapper: QuestionWithAnswersDataEntityMapper,
    gamePlayersListMapper: GamePlayersListMapper,
    playerMapper: PlayerMapper,
    opponentMapper: OpponentMapper
) = this.map {
    when (it) {
        is GameEvent.Error -> OnlineGameEvent.Error(it.message)
        is GameEvent.RoomCreated -> OnlineGameEvent.RoomCreated(roomIdEntityMapper.mapTo(it.roomIdEntity))
        is GameEvent.NextQuestion -> OnlineGameEvent.NextQuestion(questionWithAnswersDataEntityMapper.mapTo(it.question))
        is GameEvent.GameStart -> OnlineGameEvent.GameStart(questionWithAnswersDataEntityMapper.mapTo(it.question))
        is GameEvent.GameEnd -> OnlineGameEvent.GameEnd(gamePlayersListMapper.mapTo(it.players))
        is GameEvent.OpponentDisconnected -> OnlineGameEvent.OpponentDisconnected(playerMapper.mapTo(it.player))
        is GameEvent.WaitingOpponentAnswer -> OnlineGameEvent.WaitingOpponentAnswer(opponentMapper.mapTo(it.opponent))
        GameEvent.PlayerConnected -> OnlineGameEvent.PlayerConnected
        GameEvent.WaitingNextQuestion -> OnlineGameEvent.WaitingNextQuestion
        GameEvent.AnswerTimeout -> OnlineGameEvent.AnswerTimeout
        GameEvent.FailurePlayerConnection -> OnlineGameEvent.FailurePlayerConnection
        GameEvent.GameError -> OnlineGameEvent.GameError
        GameEvent.PlayerDisconnected -> OnlineGameEvent.PlayerDisconnected
        GameEvent.PlayerInGame -> OnlineGameEvent.PlayerInGame
        GameEvent.PlayerNotInGame -> OnlineGameEvent.PlayerNotInGame
        GameEvent.Unexpected -> OnlineGameEvent.Unexpected
        GameEvent.UserNotFound -> OnlineGameEvent.UserNotFound
    }
}
















