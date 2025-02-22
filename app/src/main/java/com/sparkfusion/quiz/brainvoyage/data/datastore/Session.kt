package com.sparkfusion.quiz.brainvoyage.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Session @Inject constructor(
    private val dataStore: DataStore<Preferences>
): ISession {

    override fun readUserToken(): Flow<String> {
        try {
            return dataStore.data.map { preferences ->
                preferences[tokenKey] ?: ""
            }
        } catch (exception: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun saveUserToken(token: String) {
        try {
            dataStore.edit { preferences ->
                preferences[tokenKey] = token
            }
        } catch (exception: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun clearUserToken() {
        try {
            dataStore.edit { preferences ->
                preferences.remove(tokenKey)
            }
        } catch (exception: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    companion object {

        private const val TOKEN_NAME = "token"

        @JvmStatic
        private val tokenKey = stringPreferencesKey(TOKEN_NAME)
    }
}
