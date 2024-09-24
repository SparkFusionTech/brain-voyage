package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.LoginUserDataEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LoginApiService {

    @Multipart
    @POST("/users/create")
    suspend fun registerUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part accountIcon: MultipartBody.Part?
    ): Response<LoginUserDataEntity>
}