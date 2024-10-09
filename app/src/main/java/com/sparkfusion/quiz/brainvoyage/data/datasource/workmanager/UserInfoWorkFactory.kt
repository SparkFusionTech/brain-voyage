package com.sparkfusion.quiz.brainvoyage.data.datasource.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sparkfusion.quiz.brainvoyage.data.datasource.LoginApiService
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserInfoWorkFactory @Inject constructor(
    private val loginApiService: LoginApiService,
    private val accountInfoStore: IAccountInfoStore
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            UserInfoWorker::class.java.name -> UserInfoWorker(appContext, workerParameters, loginApiService, accountInfoStore)
            else -> null
        }
    }
}
