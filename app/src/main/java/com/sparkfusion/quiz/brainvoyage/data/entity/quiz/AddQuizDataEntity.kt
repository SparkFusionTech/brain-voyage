package com.sparkfusion.quiz.brainvoyage.data.entity.quiz

import com.google.gson.annotations.SerializedName

data class AddQuizDataEntity(

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("questions")
    val questions: Int,

    @SerializedName("catalogId")
    val catalogId: Long
)
