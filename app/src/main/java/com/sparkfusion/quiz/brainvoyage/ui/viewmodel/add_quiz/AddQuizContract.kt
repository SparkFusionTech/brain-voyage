package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz

import android.graphics.Bitmap
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface AddQuizContract {

    sealed interface AddQuizIntent : Intent {
        data class ChangeTitle(val value: String): AddQuizIntent
        data class ChangeDescription(val value: String): AddQuizIntent
        data class ChangeImageSelectionDialogVisibility(val value: Boolean): AddQuizIntent
        data class ChangeIcon(val bitmap: Bitmap?): AddQuizIntent
    }

    data class AddQuizState(
        val title: String = "",
        val description: String = "",
        val showImageSelectionDialog: Boolean = false,
        val bitmap: Bitmap? = null
    ): UIState
}
