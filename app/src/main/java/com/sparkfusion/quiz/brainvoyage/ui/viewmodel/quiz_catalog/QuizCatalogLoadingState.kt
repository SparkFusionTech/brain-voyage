package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_catalog

import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel

sealed interface QuizCatalogLoadingState {
    data object Initial : QuizCatalogLoadingState
    data object Loading : QuizCatalogLoadingState
    data object Error : QuizCatalogLoadingState
    data object NetworkError : QuizCatalogLoadingState
    data class Success(val data: List<QuizCatalogModel>) : QuizCatalogLoadingState
}
