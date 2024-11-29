package com.sparkfusion.quiz.brainvoyage.data.entity.answer

import com.google.gson.annotations.SerializedName

data class AnswerDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("number")
    val number: Int,

    @SerializedName("correct")
    val correct: Boolean
)
