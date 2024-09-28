package com.sparkfusion.quiz.brainvoyage.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISession {

    fun readUserToken(): Flow<String>
    suspend fun saveUserToken(token: String)
    suspend fun clearUserToken()
}