package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add

import android.graphics.Bitmap
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface AddQuizContract {

    sealed interface AddQuizIntent : Intent {
        data class SetCatalog(val quizCatalogSerializable: QuizCatalogSerializable) : AddQuizIntent
        data class ChangeTitle(val value: String) : AddQuizIntent
        data class ChangeDescription(val value: String) : AddQuizIntent
        data class ChangeImageSelectionDialogVisibility(val value: Boolean) : AddQuizIntent
        data class ChangeTagAddingDialogVisibility(val value: Boolean) : AddQuizIntent
        data class AddTag(val tag: String) : AddQuizIntent
        data class DeleteTag(val id: Int) : AddQuizIntent
        data class ChangeIcon(val bitmap: Bitmap?) : AddQuizIntent
        data object LoadSendQuizState : AddQuizIntent
        data object ClearSendQuizState : AddQuizIntent
    }

    data class AddQuizState(
        val title: String = "",
        val description: String = "",
        val showImageSelectionDialog: Boolean = false,
        val showTagAddingDialog: Boolean = false,
        val bitmap: Bitmap? = null,
        val catalogId: Long = -1L
    ) : UIState

}
