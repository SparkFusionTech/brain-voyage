package com.sparkfusion.quiz.brainvoyage.data.entity.question

import com.google.gson.annotations.SerializedName

data class QuestionDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("category")
    val category: Int,

    @SerializedName("difficulty")
    val difficulty: Int,

    @SerializedName("explanation")
    val explanation: String
)
