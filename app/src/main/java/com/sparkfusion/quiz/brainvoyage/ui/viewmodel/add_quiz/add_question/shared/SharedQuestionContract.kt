package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface SharedQuestionContract {

    sealed interface Intent : CommonIntent {
        data class AddQuestion(val question: SendQuestionModel) : Intent
        data class DeleteQuestion(val id: Int) : Intent
    }
}