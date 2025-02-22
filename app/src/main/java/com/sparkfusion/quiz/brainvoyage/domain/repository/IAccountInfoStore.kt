package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.data.entity.AccountInfoDataEntity
import kotlinx.coroutines.flow.Flow

interface IAccountInfoStore {

    suspend fun readAccountInfo(): Flow<AccountInfoDataEntity>

    suspend fun saveAccountInfo(accountInfoDataEntity: AccountInfoDataEntity)

    suspend fun clearAccountInfo()
}
