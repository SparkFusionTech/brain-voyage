package com.sparkfusion.quiz.brainvoyage.ui.model

import kotlinx.serialization.Serializable

const val QUIZ_CATALOG_INFO_KEY = "quiz catalog info key"

@Serializable
data class QuizCatalogSerializable(
    val id: Long,
    val name: String
)
