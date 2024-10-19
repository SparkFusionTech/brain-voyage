package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.handler

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.SendQuizAnswer

@Composable
fun SendModelHandler(
    sendModelState: SendQuizAnswer,
    snackbarHostState: SnackbarHostState,
    onSuccess: (AddQuizInitialModel) -> Unit,
    clearState: () -> Unit
) {
    when (sendModelState) {
        SendQuizAnswer.DescriptionIsTooShort -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar("Description is too short")
                clearState()
            }
        }

        SendQuizAnswer.Empty -> {}

        SendQuizAnswer.ImageIsNotSelected -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar("Image is not selected")
                clearState()
            }
        }

        is SendQuizAnswer.Success -> {
            onSuccess(sendModelState.quiz)
            clearState()
        }

        SendQuizAnswer.TitleIsTooShort -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar("Title is too short")
                clearState()
            }
        }
    }
}












