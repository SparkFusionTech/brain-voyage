package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.catalog_item

import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface CatalogItemContract {

    sealed interface Intent : CommonIntent {
        data class LoadQuizzes(val catalogId: Long) : Intent
        data class SaveScrollState(val scrollPosition: Int) : Intent
    }

    sealed interface QuizLoadingState {
        data object Initial : QuizLoadingState
        data object Loading : QuizLoadingState
        data object Error : QuizLoadingState
        data object NetworkError : QuizLoadingState
        data class Success(val data: List<GetQuizPreviewModel>) : QuizLoadingState
    }
}
