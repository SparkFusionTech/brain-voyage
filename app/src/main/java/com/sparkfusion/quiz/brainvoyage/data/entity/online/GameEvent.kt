package com.sparkfusion.quiz.brainvoyage.data.entity.online

import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionWithAnswersDataEntity

sealed interface GameEvent {

    /** Events for 1xx codes */
    data class RoomCreated(val roomIdEntity: RoomIdEntity) : GameEvent
    data object PlayerConnected : GameEvent
    data class OpponentDisconnected(val player: PlayerEntity) : GameEvent
    data object PlayerDisconnected : GameEvent

    /** Events for 2xx codes */
    data class GameStart(val question: QuestionWithAnswersDataEntity) : GameEvent
    data class GameEnd(val players: GamePlayersListEntity) : GameEvent
    data class NextQuestion(val question: QuestionWithAnswersDataEntity) : GameEvent
    data class WaitingOpponentAnswer(val opponent: OpponentEntity) : GameEvent
    data object WaitingNextQuestion : GameEvent

    /** Events for 4xx codes */
    data object Unexpected : GameEvent
    data object PlayerInGame : GameEvent
    data object UserNotFound : GameEvent
    data object PlayerNotInGame : GameEvent
    data object GameError : GameEvent

    /** Events for 5xx codes */
    data object FailurePlayerConnection : GameEvent
    data object AnswerTimeout : GameEvent

    /** Unexpected events and errors */
    data class Error(val message: String) : GameEvent
}














