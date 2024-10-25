package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface SharedQuizContract {

    sealed interface Intent : CommonIntent {
        data class SetAddQuizInitialModel(val addQuizInitialModel: AddQuizInitialModel) : Intent
        data object ClearQuizModel : Intent
    }
}
