package com.sparkfusion.quiz.brainvoyage.data.entity.answer

import com.google.gson.annotations.SerializedName

data class AddAnswerDataEntity(

    @SerializedName("name")
    val name: String,

    @SerializedName("number")
    val number: Int,

    @SerializedName("isCorrect")
    val isCorrect: Boolean
)
