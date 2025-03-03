package com.sparkfusion.quiz.brainvoyage.data.entity.online

import com.google.gson.Gson
import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionWithAnswersDataEntity
import javax.inject.Inject

class GameEventMapper @Inject constructor() {

    fun map(response: ServerResponse<*>, gson: Gson): GameEvent {
        return when (response.code) {
            100 -> {
                val roomIdEntity =
                    gson.fromJson(gson.toJson(response.data), RoomIdEntity::class.java)
                GameEvent.RoomCreated(roomIdEntity)
            }

            101 -> GameEvent.PlayerConnected
            102 -> {
                val player = gson.fromJson(gson.toJson(response.data), PlayerEntity::class.java)
                GameEvent.OpponentDisconnected(player)
            }

            103 -> GameEvent.PlayerDisconnected

            200 -> {
                val questionEntity = gson.fromJson(
                    gson.toJson(response.data),
                    QuestionWithAnswersDataEntity::class.java
                )
                GameEvent.GameStart(questionEntity)
            }

            201 -> {
                val players = gson.fromJson(
                    gson.toJson(response.data),
                    GamePlayersListEntity::class.java
                )
                GameEvent.GameEnd(players)
            }

            202 -> {
                val questionEntity = gson.fromJson(
                    gson.toJson(response.data),
                    QuestionWithAnswersDataEntity::class.java
                )
                GameEvent.NextQuestion(questionEntity)
            }

            203 -> {
                val opponent = gson.fromJson(
                    gson.toJson(response.data),
                    OpponentEntity::class.java
                )
                GameEvent.WaitingOpponentAnswer(opponent)
            }

            204 -> GameEvent.WaitingNextQuestion

            400 -> GameEvent.Unexpected
            401 -> GameEvent.PlayerInGame
            402 -> GameEvent.UserNotFound
            403 -> GameEvent.PlayerNotInGame
            404 -> GameEvent.GameError

            500 -> GameEvent.FailurePlayerConnection
            501 -> GameEvent.AnswerTimeout

            else -> GameEvent.Error(message = response.message)
        }
    }
}




















