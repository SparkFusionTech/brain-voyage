package com.sparkfusion.quiz.brainvoyage.data.entity

import com.google.gson.annotations.SerializedName

data class UserExistsDataEntity(

    @SerializedName("exists")
    val exists: Boolean
)