package com.sparkfusion.quiz.brainvoyage.domain.model.answer

data class PlayAnswerModel(
    val id: Long,
    val name: String,
    val number: Int,
    val correct: Boolean,
    val isSelected: Boolean = false
)
