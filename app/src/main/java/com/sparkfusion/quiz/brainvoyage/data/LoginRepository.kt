package com.sparkfusion.quiz.brainvoyage.data

import com.sparkfusion.quiz.brainvoyage.data.datasource.LoginApiService
import com.sparkfusion.quiz.brainvoyage.ui.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    @IODispatcher ioDispatcher: CoroutineDispatcher
) : ILoginRepository {

}