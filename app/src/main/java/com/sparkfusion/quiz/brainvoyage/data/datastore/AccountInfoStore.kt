package com.sparkfusion.quiz.brainvoyage.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sparkfusion.quiz.brainvoyage.data.entity.AccountInfoDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountInfoStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IAccountInfoStore {

    override suspend fun readAccountInfo(): Flow<AccountInfoDataEntity> {
        try {
            return dataStore.data.map { preferences ->
                AccountInfoDataEntity(
                    preferences[userNameKey] ?: "",
                    preferences[accountIconKey] ?: ""
                )
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun saveAccountInfo(accountInfoDataEntity: AccountInfoDataEntity) {
        try {
            dataStore.edit { preferences ->
                preferences[userNameKey] = accountInfoDataEntity.name
                preferences[accountIconKey] = accountInfoDataEntity.iconUrl
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    override suspend fun clearAccountInfo() {
        try {
            dataStore.edit { preferences ->
                preferences.remove(userNameKey)
                preferences.remove(accountIconKey)
            }
        } catch (e: Exception) {
            throw FailedDataStoreOperationException()
        }
    }

    companion object {

        private const val USER_NAME = "user name"
        private const val ACCOUNT_ICON_NAME = "account icon"

        @JvmStatic
        private val userNameKey = stringPreferencesKey(USER_NAME)

        @JvmStatic
        private val  accountIconKey = stringPreferencesKey(ACCOUNT_ICON_NAME)
    }
}
