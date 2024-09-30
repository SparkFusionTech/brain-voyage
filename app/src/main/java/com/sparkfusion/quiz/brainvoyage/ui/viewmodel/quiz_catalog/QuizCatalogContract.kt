package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface QuizCatalogContract {

    sealed interface QuizCatalogIntent : Intent {
        data object LoadQuizCatalog : QuizCatalogIntent
    }

    data class QuizCatalogState(
        val catalogLoadingState: QuizCatalogLoadingState = QuizCatalogLoadingState.Loading
    ) : UIState
}
