package com.sparkfusion.quiz.brainvoyage.domain.model.question

data class AddQuestionModel(
    val name: String,
    val category: Int,
    val difficulty: Int,
    val explanation: String,
    val quizId: Long,
    val answers: List<AddAnswerModel>
)
