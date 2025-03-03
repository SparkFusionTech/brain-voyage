package com.sparkfusion.quiz.brainvoyage.data.repository

import com.google.gson.Gson
import com.sparkfusion.quiz.brainvoyage.data.datasource.online.WebSocketManager
import com.sparkfusion.quiz.brainvoyage.data.entity.online.AnswerOnQuestionModel
import com.sparkfusion.quiz.brainvoyage.data.entity.online.ServerRequest
import com.sparkfusion.quiz.brainvoyage.domain.mapper.online.GamePlayersListMapper
import com.sparkfusion.quiz.brainvoyage.domain.mapper.online.OpponentMapper
import com.sparkfusion.quiz.brainvoyage.domain.mapper.online.PlayerMapper
import com.sparkfusion.quiz.brainvoyage.domain.mapper.online.RoomIdEntityMapper
import com.sparkfusion.quiz.brainvoyage.domain.mapper.online.map
import com.sparkfusion.quiz.brainvoyage.domain.mapper.question.QuestionWithAnswersDataEntityMapper
import com.sparkfusion.quiz.brainvoyage.domain.model.online.AccountEmailEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountEmailStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.IOnline2VS2GameRepository
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Online2VS2GameRepository @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val webSocketManager: WebSocketManager,
    private val gson: Gson,
    private val accountEmailStore: IAccountEmailStore,
    roomIdEntityMapper: RoomIdEntityMapper,
    questionWithAnswersDataEntityMapper: QuestionWithAnswersDataEntityMapper,
    gamePlayersListMapper: GamePlayersListMapper,
    playerMapper: PlayerMapper,
    opponentMapper: OpponentMapper
) : IOnline2VS2GameRepository {

    private val coroutineScope = CoroutineScope(
        CoroutineName("Event mapping scope") + defaultDispatcher + SupervisorJob()
    )

    override val events: SharedFlow<OnlineGameEvent> = webSocketManager.events
        .map(roomIdEntityMapper, questionWithAnswersDataEntityMapper, gamePlayersListMapper, playerMapper, opponentMapper)
        .shareIn(coroutineScope, SharingStarted.Lazily, replay = 1)

    override suspend fun connect() {
        webSocketManager.connect()
    }

    override suspend fun disconnect() {
        webSocketManager.disconnect()
    }

    override suspend fun joinOrCreateRoom(catalogId: Long?) {
        val email = accountEmailStore.readAccountEmail().firstOrNull() ?: ""
        val accountEmailEntity = AccountEmailEntity(email, catalogId)

        val request =
            ServerRequest(ServerRequest.Actions.JOIN_OR_CREATE.content, accountEmailEntity)
        webSocketManager.sendMessage(gson.toJson(request))
    }

    override suspend fun answerOnQuestion(score: Int) {
        val answerOnQuestionModel = AnswerOnQuestionModel(score)
        val request = ServerRequest(ServerRequest.Actions.ANSWER_ON_QUESTION.content, answerOnQuestionModel)
        webSocketManager.sendMessage(gson.toJson(request))
    }

    override suspend fun nextQuestion() {
        val request = ServerRequest(ServerRequest.Actions.NEXT_QUESTION.content, null)
        webSocketManager.sendMessage(gson.toJson(request))
    }

    override suspend fun disconnectFromRoom() {
        val request = ServerRequest(ServerRequest.Actions.DISCONNECT.content, null)
        webSocketManager.sendMessage(gson.toJson(request))
    }
}


























