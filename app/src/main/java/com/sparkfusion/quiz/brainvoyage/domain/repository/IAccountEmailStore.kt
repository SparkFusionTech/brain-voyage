package com.sparkfusion.quiz.brainvoyage.domain.repository

import kotlinx.coroutines.flow.Flow

interface IAccountEmailStore {

    suspend fun readAccountEmail(): Flow<String>

    suspend fun clearAccountEmail()

    suspend fun changeAccountEmail(email: String): Flow<String>
}




















