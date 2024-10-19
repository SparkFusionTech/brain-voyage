package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface AddQuizWithQuestionsContract {

    sealed interface Intent : CommonIntent {
        data class ChangeCloseDialogVisibility(val value: Boolean) : Intent
    }

    data class State(
        val showCloseDialog: Boolean = false
    ) : UIState
}
