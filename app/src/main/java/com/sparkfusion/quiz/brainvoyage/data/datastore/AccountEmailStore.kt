package com.sparkfusion.quiz.brainvoyage.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountEmailStore
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountEmailStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IAccountEmailStore {

    override suspend fun readAccountEmail(): Flow<String> {
        try {
            return dataStore.data.map { preferences ->
                preferences[accountEmail] ?: ""
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun clearAccountEmail() {
        try {
            dataStore.edit { preferences ->
                preferences.remove(accountEmail)
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun changeAccountEmail(email: String): Flow<String> {
        try {
            dataStore.edit { preferences ->
                preferences[accountEmail] = email
            }
            return readAccountEmail()
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    companion object {
        private const val ACCOUNT_EMAIL = "account email"

        @JvmStatic
        private val accountEmail = stringPreferencesKey(ACCOUNT_EMAIL)
    }
}





















