package com.sparkfusion.quiz.brainvoyage.domain.model.quiz

import java.time.LocalDateTime

data class SubmittedQuizModel(
    val id: Long,
    val title: String,
    val description: String,
    val status: QuizStatusModel,
    val rating: Double,
    val imageUrl: String,
    val questions: Int,
    val createdAt: LocalDateTime
)
