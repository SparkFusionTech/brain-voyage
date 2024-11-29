package com.sparkfusion.quiz.brainvoyage.domain.model.question

import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AnswerModel

data class QuestionModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val category: Int,
    val difficulty: Int,
    val explanation: String,
    val answers: List<AnswerModel> = listOf()
)
