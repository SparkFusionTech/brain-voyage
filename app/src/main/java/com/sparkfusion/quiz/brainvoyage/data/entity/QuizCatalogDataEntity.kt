package com.sparkfusion.quiz.brainvoyage.data.entity

import com.google.gson.annotations.SerializedName

data class QuizCatalogDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("startGradientColor")
    val startGradientColor: String,

    @SerializedName("endGradientColor")
    val endGradientColor: String
)
