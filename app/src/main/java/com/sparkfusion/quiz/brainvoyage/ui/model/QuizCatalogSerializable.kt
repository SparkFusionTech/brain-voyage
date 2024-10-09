package com.sparkfusion.quiz.brainvoyage.ui.model

import kotlinx.serialization.Serializable
import java.io.Serializable as SerializableInterface

const val QUIZ_CATALOG_INFO_KEY = "quiz catalog info key"

@Serializable
data class QuizCatalogSerializable(
    val id: Long,
    val name: String
): SerializableInterface
