package com.sparkfusion.quiz.brainvoyage.data.entity.quiz.rating

import com.google.gson.annotations.SerializedName

data class UserQuizRatingDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("rating")
    val rating: Int
)
