package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.data.entity.LoginUserDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.AccountInfoModel
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.domain.model.TokenModel
import com.sparkfusion.quiz.brainvoyage.domain.model.UserExistsModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ILoginRepository {

    suspend fun registerAccount(
        email: RequestBody,
        password: RequestBody,
        accountIcon: MultipartBody.Part?
    ): Answer<LoginUserModel>

    suspend fun authenticate(
        user: LoginUserDataEntity
    ): Answer<TokenModel>

    suspend fun loadUserInfo(): Answer<AccountInfoModel>

    suspend fun exists(email: String): Answer<UserExistsModel>

    suspend fun checkTokenValidation(): Answer<Unit>
}
