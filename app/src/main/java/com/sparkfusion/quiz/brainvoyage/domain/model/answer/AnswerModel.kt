package com.sparkfusion.quiz.brainvoyage.domain.model.answer

data class AnswerModel(
    val id: Long,
    val name: String,
    val number: Int,
    val correct: Boolean
)
