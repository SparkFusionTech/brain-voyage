package com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress

data class CatalogProgressQuestionModel(
    val id: Long,
    val questionId: Long,
    val xpGained: Int,
    val correctAnswer: Boolean
)
