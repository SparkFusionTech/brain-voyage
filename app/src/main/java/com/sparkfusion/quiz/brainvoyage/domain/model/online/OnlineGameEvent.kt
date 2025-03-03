package com.sparkfusion.quiz.brainvoyage.domain.model.online

import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel

sealed interface OnlineGameEvent {
    data class RoomCreated(val roomIdModel: RoomIdModel) : OnlineGameEvent
    data object PlayerConnected : OnlineGameEvent
    data class OpponentDisconnected(val player: PlayerModel) : OnlineGameEvent
    data object PlayerDisconnected : OnlineGameEvent

    data class GameStart(val question: QuestionWithAnswersModel) : OnlineGameEvent
    data class GameEnd(val players: GamePlayersListModel) : OnlineGameEvent
    data class NextQuestion(val question: QuestionWithAnswersModel) : OnlineGameEvent
    data class WaitingOpponentAnswer(val opponent: OpponentModel) : OnlineGameEvent
    data object WaitingNextQuestion : OnlineGameEvent

    data object Unexpected : OnlineGameEvent
    data object PlayerInGame : OnlineGameEvent
    data object UserNotFound : OnlineGameEvent
    data object PlayerNotInGame : OnlineGameEvent
    data object GameError : OnlineGameEvent

    data object FailurePlayerConnection : OnlineGameEvent
    data object AnswerTimeout : OnlineGameEvent

    data class Error(val message: String) : OnlineGameEvent
}



























