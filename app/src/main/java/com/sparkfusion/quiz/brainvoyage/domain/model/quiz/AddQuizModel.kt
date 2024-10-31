package com.sparkfusion.quiz.brainvoyage.domain.model.quiz

data class AddQuizModel(
    val title: String,
    val description: String,
    val questions: Int,
    val catalogId: Long
)
