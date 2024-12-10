package com.sparkfusion.quiz.brainvoyage.ui.screen.play.model

import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty

data class AnsweredQuestionModel(
    val id: Long,
    val category: Int,
    val difficulty: QuestionDifficulty,
    val isCorrectAnswer: Boolean
)
