package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add

import android.graphics.Bitmap
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface AddQuestionContract {

    sealed interface Intent : CommonIntent {
        data class ChangeIcon(val icon: Bitmap?) : Intent
        data class ChangeQuestion(val value: String) : Intent
        data class ChangeDescription(val value: String) : Intent
        data class ChangeDifficulty(val id: Int) : Intent
        data class ChangeCategory(val id: Int, val trueFalseValues: List<String>) : Intent
        data class AddAnswer(val answer: String) : Intent
        data class DeleteAnswer(val id: Int) : Intent
        data class ChangeNewAnswerDialogVisibility(val value: Boolean) : Intent
        data class ChangeCloseDialogVisibility(val value: Boolean) : Intent
        data class ChangeImageSelectionDialogVisibility(val value: Boolean) : Intent
        data class ChangeSelectedRadioButton(val id: Int) : Intent
        data class ChangeSelectedCheckButton(val id: Int, val value: Boolean) : Intent
        data class ChangeCategoryListVisibility(val value: Boolean) : Intent
        data object HandleQuestionAdding : Intent
        data object ClearQuestionAddingState : Intent
    }

    data class ImageState(
        val bitmap: Bitmap? = null,
    )

    data class CommonState(
        val question: String = "",
        val description: String = "",
        val currentDifficultyId: Int = 0,
        val currentCategoryId: Int = 0,
        val isCategoryListVisible: Boolean = false
    )

    data class DialogsState(
        val showNewAnswerDialog: Boolean = false,
        val showCloseDialog: Boolean = false,
        val showImageSelectionDialog: Boolean = false
    )

    sealed interface ErrorState {
        data class Success(val sendQuestionModel: SendQuestionModel) : ErrorState
        data object EmptyImage : ErrorState
        data object ShortQuestion : ErrorState
        data object NotEnoughAnswers : ErrorState
        data object NoCorrectAnswer : ErrorState
        data object Empty : ErrorState
    }
}













