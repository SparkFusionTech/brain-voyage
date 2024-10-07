package com.sparkfusion.quiz.brainvoyage.data.entity

import com.google.gson.annotations.SerializedName

data class ImageSearchDataEntity(

    @SerializedName("webformatURL")
    val url: String,

    @SerializedName("webformatWidth")
    val width: Int,

    @SerializedName("webformatHeight")
    val height: Int
)
