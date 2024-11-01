package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.handler

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add.AddQuestionContract

@Composable
fun HandleQuestionAddingComponent(
    state: AddQuestionContract.ErrorState,
    snackbarHostState: SnackbarHostState,
    onSuccess: (SendQuestionModel) -> Unit,
    onClearState: () -> Unit
) {
    val emptyImageMessage = stringResource(id = R.string.image_is_not_selected)
    val noCorrectAnswerMessage = stringResource(id = R.string.correct_answer_was_not_selected)
    val notEnoughAnswersMessage = stringResource(id = R.string.not_enough_answers)
    val shortQuestionMessage = stringResource(id = R.string.questions_name_is_too_short)
    LaunchedEffect(state) {
        when (state) {
            AddQuestionContract.ErrorState.Empty -> {}
            AddQuestionContract.ErrorState.EmptyImage -> {
                snackbarHostState.showSnackbar(emptyImageMessage)
                onClearState()
            }
            AddQuestionContract.ErrorState.NoCorrectAnswer -> {
                snackbarHostState.showSnackbar(noCorrectAnswerMessage)
                onClearState()
            }
            AddQuestionContract.ErrorState.NotEnoughAnswers -> {
                snackbarHostState.showSnackbar(notEnoughAnswersMessage)
                onClearState()
            }
            AddQuestionContract.ErrorState.ShortQuestion -> {
                snackbarHostState.showSnackbar(shortQuestionMessage)
                onClearState()
            }
            is AddQuestionContract.ErrorState.Success -> {
                onSuccess(state.sendQuestionModel)
            }
        }
    }
}























