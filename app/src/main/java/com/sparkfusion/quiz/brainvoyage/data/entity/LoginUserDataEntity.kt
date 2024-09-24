package com.sparkfusion.quiz.brainvoyage.data.entity

import com.google.gson.annotations.SerializedName

data class LoginUserDataEntity(

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
