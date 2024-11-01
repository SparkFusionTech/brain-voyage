package com.sparkfusion.quiz.brainvoyage.domain.model.quiz

data class GetQuizPreviewModel(
    val id: Long,
    val title: String,
    val description: String,
    val rating: Double,
    val imageUrl: String,
    val questions: Int
)
