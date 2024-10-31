package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface AddQuizWithQuestionsContract {

    sealed interface Intent : CommonIntent {
        data object ClearSavingState : Intent
        data object ClearQuizVerificationState : Intent
        data class ChangeCloseDialogVisibility(val value: Boolean) : Intent
        data class ChangePublicationDialogVisibility(val value: Boolean) : Intent
        data class SaveQuiz(
            val addQuizInitialModel: AddQuizInitialModel?,
            val questions: List<SendQuestionModel>
        ) : Intent
    }

    data class State(
        val showCloseDialog: Boolean = false,
        val showPublicationDialog: Boolean = false
    ) : UIState

    sealed interface QuizVerificationState : UIState {
        data object QuizVerificationIsNull : QuizVerificationState
        data object Empty : QuizVerificationState
        data object NotEnoughQuestions : QuizVerificationState
    }

    sealed interface QuizSavingState : UIState {
        data object Empty : QuizSavingState
        data object QuizSaving : QuizSavingState
        data object QuizSavingError : QuizSavingState
        data object TagsSaving : QuizSavingState
        data object TagsSavingError : QuizSavingState
        data object QuestionsSaving : QuizSavingState
        data object QuestionsSavingError : QuizSavingState
        data object Success : QuizSavingState
    }
}













