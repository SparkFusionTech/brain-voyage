package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface AddQuizWithQuestionsContract {

    sealed interface Intent : CommonIntent {
        data object ClearPublishState : Intent
        data class ChangeCloseDialogVisibility(val value: Boolean) : Intent
        data class SaveQuiz(
            val addQuizInitialModel: AddQuizInitialModel?,
            val questions: List<SendQuestionModel>
        ) : Intent
    }

    data class State(
        val showCloseDialog: Boolean = false
    ) : UIState

    sealed interface PublishQuizState : UIState {
        data object QuizIsNull : PublishQuizState
        data object Empty : PublishQuizState
        data object NotEnoughQuestions : PublishQuizState
        data object Success : PublishQuizState
    }

    sealed interface QuizSavingState : UIState {
        data object Empty : QuizSavingState
        data object QuizSaving : QuizSavingState
        data object TagsSaving : QuizSavingState
        data object QuestionsSaving : QuizSavingState
        data object Error : QuizSavingState
        data object Success : QuizSavingState
    }
}













