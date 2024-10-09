package com.sparkfusion.quiz.brainvoyage.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sparkfusion.quiz.brainvoyage.data.entity.AccountInfoDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountInfoStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IAccountInfoStore {

    override suspend fun readAccountInfo(): Flow<AccountInfoDataEntity> {
        return dataStore.data.map { preferences ->
            AccountInfoDataEntity(
                preferences[userNameKey] ?: "",
                preferences[accountIconKey] ?: ""
            )
        }
    }

    override suspend fun saveAccountInfo(accountInfoDataEntity: AccountInfoDataEntity) {
        dataStore.edit { preferences ->
            preferences[userNameKey] = accountInfoDataEntity.name
            preferences[accountIconKey] = accountInfoDataEntity.iconUrl
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
