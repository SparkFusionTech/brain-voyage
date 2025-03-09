package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.AccountDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.AccountInfoDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.UserExistsDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.LoginUserDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.TokenDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.TokenValidationDataEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface LoginApiService {

    @Multipart
    @POST("/users/create")
    suspend fun registerUser(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part accountIcon: MultipartBody.Part?
    ): Response<LoginUserDataEntity>

    @POST("/users/authentication")
    suspend fun authenticate(@Body getUserDto: LoginUserDataEntity): Response<TokenDataEntity>

    @GET("/users/info")
    suspend fun readUserInfo(): Response<AccountInfoDataEntity>

    @GET("/users/exists")
    suspend fun exists(@Query("email") email: String): Response<UserExistsDataEntity>

    @GET("/users/check-token")
    suspend fun checkTokenValidation(): Response<TokenValidationDataEntity>

    @POST("/users/password")
    suspend fun changePassword(@Query("password") password: String): Response<AccountDataEntity>

    @DELETE("/users")
    suspend fun deleteAccount(@Query("password") password: String): Response<Unit>
}

















