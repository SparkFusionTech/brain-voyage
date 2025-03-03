package com.sparkfusion.quiz.brainvoyage.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISaveAccountSignInStore
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAccountSignInStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ISaveAccountSignInStore {

    override suspend fun readSaveAccountSignIn(): Flow<Boolean> {
        try {
            return dataStore.data.map {  preferences ->
                preferences[saveSignIn] ?: true
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun clearSaveAccountSingIn() {
        try {
            dataStore.edit { preferences ->
                preferences.remove(saveSignIn)
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun changeSaveAccountSignIn(value: Boolean): Flow<Boolean> {
        try {
            dataStore.edit { preferences ->
                preferences[saveSignIn] = value
            }
            return readSaveAccountSignIn()
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    companion object {

        private const val SAVE_SIGN_IN = "save sign in"

        @JvmStatic
        private val saveSignIn = booleanPreferencesKey(SAVE_SIGN_IN)
    }
}
















