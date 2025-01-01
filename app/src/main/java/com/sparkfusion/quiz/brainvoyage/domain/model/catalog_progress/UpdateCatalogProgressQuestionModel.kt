package com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress

data class UpdateCatalogProgressQuestionModel(
    val quizId: Long,
    val questionId: Long,
    val xpGained: Int,
    val correctAnswer: Boolean
)
