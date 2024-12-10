package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play

import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.model.AnsweredQuestionModel
import javax.inject.Inject

class AnsweredQuestionFactory @Inject constructor() {

    fun mapTo(input: QuestionWithAnswersModel, isCorrect: Boolean): AnsweredQuestionModel = with(input) {
        return AnsweredQuestionModel(id, category, difficulty, isCorrect)
    }
}