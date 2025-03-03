package com.sparkfusion.quiz.brainvoyage.data.datasource.online

import android.util.Log
import com.google.gson.Gson
import com.sparkfusion.quiz.brainvoyage.data.entity.online.GameEvent
import com.sparkfusion.quiz.brainvoyage.data.entity.online.GameEventMapper
import com.sparkfusion.quiz.brainvoyage.data.entity.online.ServerResponse
import com.sparkfusion.quiz.brainvoyage.data.utils.WEBSOCKET_URL
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketManager @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val client: OkHttpClient,
    private val gson: Gson,
    private val gameEventMapper: GameEventMapper
) {

    private var webSocket: WebSocket? = null

    private val _events = MutableSharedFlow<GameEvent>(replay = 1, extraBufferCapacity = 3)
    val events: SharedFlow<GameEvent> = _events.asSharedFlow()

    private val webSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                Log.d("TAGTAG", "TEXT - $text")
                val response = gson.fromJson(text, ServerResponse::class.java)
                Log.d("TAGTAG", "RESPONSE - $response")
                val event = gameEventMapper.map(response, gson)
                Log.d("TAGTAG", event.toString())
                _events.tryEmit(event)
            } catch (e: Exception) {
                Log.d("TAGTAG", e.message.toString())
                _events.tryEmit(GameEvent.Error(e.message.toString()))
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
            Log.d("TAGTAG", "Connection closed: Code $code Reason: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            _events.tryEmit(GameEvent.Error(t.message ?: "WebSocket error"))
        }
    }

    suspend fun connect() = withContext(ioDispatcher) {
        val request = Request.Builder().url(WEBSOCKET_URL).build()
        webSocket = client.newWebSocket(request, webSocketListener)
    }

    suspend fun sendMessage(message: String) = withContext(ioDispatcher) {
        webSocket?.send(message)
    }

    suspend fun disconnect() = withContext(ioDispatcher) {
        webSocket?.close(1000, "Closing connection")
        webSocket = null
    }
}





















