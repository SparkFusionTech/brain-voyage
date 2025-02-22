package com.sparkfusion.quiz.brainvoyage.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISaveAccountSignInStore {

    suspend fun readSaveAccountSignIn(): Flow<Boolean>

    suspend fun clearSaveAccountSingIn()

    suspend fun changeSaveAccountSignIn(value: Boolean): Flow<Boolean>
}