package com.sparkfusion.quiz.brainvoyage.domain.model.question

import com.sparkfusion.quiz.brainvoyage.domain.model.answer.PlayAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty

data class QuestionWithAnswersModel(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val category: Int,
    val difficulty: QuestionDifficulty,
    val explanation: String,
    val answers: List<PlayAnswerModel>
)
