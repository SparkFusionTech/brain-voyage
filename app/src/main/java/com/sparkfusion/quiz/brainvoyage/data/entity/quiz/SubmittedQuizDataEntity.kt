package com.sparkfusion.quiz.brainvoyage.data.entity.quiz

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import com.sparkfusion.quiz.brainvoyage.data.entity.LocalDateTimeAdapter
import java.time.LocalDateTime

data class SubmittedQuizDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("status")
    val status: Int,

    @SerializedName("rating")
    val rating: Double,

    @SerializedName("imageUrl")
    val imageUrl: String,

    @SerializedName("questions")
    val questions: Int,

    @SerializedName("createdAt")
    @JsonAdapter(LocalDateTimeAdapter::class)
    val createdAt: LocalDateTime
)












