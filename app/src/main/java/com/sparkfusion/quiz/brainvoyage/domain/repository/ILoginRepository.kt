package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ILoginRepository {

    suspend fun registerAccount(
        email: RequestBody,
        password: RequestBody,
        accountIcon: MultipartBody.Part?
    ): Answer<LoginUserModel>
}