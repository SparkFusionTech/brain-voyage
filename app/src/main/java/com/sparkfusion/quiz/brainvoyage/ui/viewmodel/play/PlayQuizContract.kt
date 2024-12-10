package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play

import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizContract
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent

interface PlayQuizContract {

    sealed interface PlayQuizIntent : Intent {
        data class ReadQuestions(val quizId: Long) : PlayQuizIntent
        data object CheckAnswers : PlayQuizIntent
        data class SelectAnswer(val answerIdInList: Int) : PlayQuizIntent
        data class NextQuestion(val quizId: Long) : PlayQuizIntent
        data class ChangeExitDialogVisibility(val isVisible: Boolean) : PlayQuizIntent
    }

    sealed class CurrentQuestionState {
        data object Loading : CurrentQuestionState()

        data class Victory(
            val victoryInitialState: VictoryQuizContract.InitialState
        ) : CurrentQuestionState()

        data class CurrentQuestion(
            val question: QuestionWithAnswersModel
        ) : CurrentQuestionState()
    }

    sealed class AnswerCheckState {
        data object Empty : AnswerCheckState()

        data class AnswerCheckResult(
            val correctAnswersIds: List<Long>,
            val incorrectAnswersIds: List<Long>,
            val isCorrect: Boolean
        ) : AnswerCheckState()
    }

    data class DialogsState(
        val isCloseDialogVisible: Boolean = false
    )

    data class ErrorState(
        val isAnswerNotSelected: Boolean = false
    )

    data class CompletedValueState(
        val percent: Float = 0f
    )
}













