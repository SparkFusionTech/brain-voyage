package com.sparkfusion.quiz.brainvoyage.data.datasource.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.datasource.LoginApiService
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import kotlinx.coroutines.coroutineScope

class UserInfoWorker(
    applicationContext: Context,
    params: WorkerParameters,
    private val loginApiService: LoginApiService,
    private val accountInfoStore: IAccountInfoStore
) : CoroutineWorker(applicationContext, params) {

    override suspend fun doWork(): Result = coroutineScope {
        val response = loginApiService.readUserInfo()
        ApiResponseHandler(response, ::handleExceptionCode)
            .handleFetchedData()
            .onSuccess { entity ->
                accountInfoStore.saveAccountInfo(entity)
            }
            .onFailure { exception ->
                return@coroutineScope when(exception) {
                    is NetworkException -> Result.retry()
                    else -> Result.failure()
                }
            }

        return@coroutineScope Result.success()
    }

    companion object {
        const val TAG = "user info worker"
    }
}
