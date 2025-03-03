package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import kotlinx.coroutines.flow.SharedFlow

interface IOnline2VS2GameRepository {

    val events: SharedFlow<OnlineGameEvent>

    suspend fun connect()
    suspend fun disconnect()

    suspend fun joinOrCreateRoom(catalogId: Long?)
    suspend fun disconnectFromRoom()
    suspend fun answerOnQuestion(score: Int)
    suspend fun nextQuestion()
}


























