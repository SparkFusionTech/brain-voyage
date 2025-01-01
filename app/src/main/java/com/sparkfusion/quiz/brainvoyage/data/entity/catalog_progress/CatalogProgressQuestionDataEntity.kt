package com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress

import com.google.gson.annotations.SerializedName

data class CatalogProgressQuestionDataEntity(

    @SerializedName("id")
    val id: Long,

    @SerializedName("questionId")
    val questionId: Long,

    @SerializedName("xpGained")
    val xpGained: Int,

    @SerializedName("correctAnswer")
    val correctAnswer: Boolean
)
