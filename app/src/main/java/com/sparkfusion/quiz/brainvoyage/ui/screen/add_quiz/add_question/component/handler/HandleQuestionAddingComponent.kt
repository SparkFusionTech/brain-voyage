package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.handler

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add.AddQuestionContract

@Composable
fun HandleQuestionAddingComponent(
    state: AddQuestionContract.ErrorState,
    snackbarHostState: SnackbarHostState,
    onSuccess: (SendQuestionModel) -> Unit
) {
    LaunchedEffect(state) {
        when (state) {
            AddQuestionContract.ErrorState.Empty -> {}
            AddQuestionContract.ErrorState.EmptyImage -> {
                snackbarHostState.showSnackbar("Image is not selected")
            }
            AddQuestionContract.ErrorState.NoCorrectAnswer -> {
                snackbarHostState.showSnackbar("Correct answer was not selected")
            }
            AddQuestionContract.ErrorState.NotEnoughAnswers -> {
                snackbarHostState.showSnackbar("Not enough answers")
            }
            AddQuestionContract.ErrorState.ShortQuestion -> {
                snackbarHostState.showSnackbar("Questions name is too short")
            }
            is AddQuestionContract.ErrorState.Success -> {
                onSuccess(state.sendQuestionModel)
            }
        }
    }
}























