package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.drawer.my_quizzes

import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface MyQuizzesContract {

    sealed interface Intent : CommonIntent {
        data object ReadQuizzes : Intent
    }

    sealed interface QuizzesReadingState : UIState {
        data object Loading : QuizzesReadingState
        data object Error : QuizzesReadingState
        data class Success(
            val map: Map<String, List<SubmittedQuizModel>>
        ) : QuizzesReadingState
    }
}