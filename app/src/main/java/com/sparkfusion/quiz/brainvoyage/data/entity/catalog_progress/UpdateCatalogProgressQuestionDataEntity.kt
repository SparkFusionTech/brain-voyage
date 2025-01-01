package com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress

import com.google.gson.annotations.SerializedName

data class UpdateCatalogProgressQuestionDataEntity(

    @SerializedName("quizId")
    val quizId: Long,

    @SerializedName("questionId")
    val questionId: Long,

    @SerializedName("xpGained")
    val xpGained: Int,

    @SerializedName("correctAnswer")
    val correctAnswer: Boolean
)
