package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent

interface QuizCatalogContract {

    sealed interface QuizCatalogIntent : Intent {
        data object LoadQuizCatalog : QuizCatalogIntent
        data object ClearReadingState : QuizCatalogIntent
    }
}
