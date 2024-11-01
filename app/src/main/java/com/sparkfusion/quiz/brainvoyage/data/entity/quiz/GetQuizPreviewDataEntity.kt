package com.sparkfusion.quiz.brainvoyage.data.entity.quiz

import com.google.gson.annotations.SerializedName

data class GetQuizPreviewDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("questions")
    val questions: Int
)
