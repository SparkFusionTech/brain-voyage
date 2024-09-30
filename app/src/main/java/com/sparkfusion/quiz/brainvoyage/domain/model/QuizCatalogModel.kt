package com.sparkfusion.quiz.brainvoyage.domain.model

data class QuizCatalogModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val startGradientColor: String,
    val endGradientColor: String
)
