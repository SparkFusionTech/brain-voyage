package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer

import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AddAnswerModel

data class QuestionAnswerModel(
    val name: String,
    val isCorrect: Boolean
) {

    fun map(number: Int): AddAnswerModel {
        return AddAnswerModel(name, number, isCorrect)
    }
}
