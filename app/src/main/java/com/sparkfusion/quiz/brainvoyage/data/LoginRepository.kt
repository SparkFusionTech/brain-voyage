package com.sparkfusion.quiz.brainvoyage.data

import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.LoginApiService
import com.sparkfusion.quiz.brainvoyage.data.mapper.LoginUserDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginApiService: LoginApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginUserDataEntityFactory: LoginUserDataEntityFactory
) : ILoginRepository {

    override suspend fun registerAccount(
        email: RequestBody,
        password: RequestBody,
        accountIcon: MultipartBody.Part?
    ): Answer<LoginUserModel> = safeApiCall(ioDispatcher) {
        val handler = ApiResponseHandler(loginApiService.registerUser(email, password, accountIcon))
        handler.handleFetchedData().suspendMap(loginUserDataEntityFactory::mapTo)
    }
}
















