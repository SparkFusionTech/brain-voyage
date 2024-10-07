package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.image

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface ImageSearchContract {

    sealed interface ImageSearchIntent : Intent {
        data object LoadImages : ImageSearchIntent
        data class ChangeQuery(val value: String) : ImageSearchIntent
        data class HandleErrorLoading(val error: Throwable) : ImageSearchIntent
    }

    data class ImageSearchState(
        val query: String = "",
        val isQueryEmpty: Boolean = true,
        val imageSearchingState: ImageSearchingState = ImageSearchingState.Empty
    ) : UIState
}
