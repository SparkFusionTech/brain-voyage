package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel

sealed interface SendQuizAnswer {
    data object Empty : SendQuizAnswer
    data object ImageIsNotSelected : SendQuizAnswer
    data object TitleIsTooShort : SendQuizAnswer
    data object DescriptionIsTooShort : SendQuizAnswer
    data class Success(val quiz: AddQuizInitialModel) : SendQuizAnswer
}
