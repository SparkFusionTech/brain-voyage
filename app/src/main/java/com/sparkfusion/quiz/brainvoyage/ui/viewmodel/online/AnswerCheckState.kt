package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online

sealed interface AnswerCheckState {
    data object Empty : AnswerCheckState
    data class Answered(
        val correctAnswersIds: List<Long>,
        val incorrectAnswersIds: List<Long>,
        val isCorrect: Boolean
    ) : AnswerCheckState
}

























