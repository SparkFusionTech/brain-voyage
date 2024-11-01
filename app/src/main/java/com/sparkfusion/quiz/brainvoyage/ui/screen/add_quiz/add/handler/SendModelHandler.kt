package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.handler

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SendQuizAnswer

@Composable
fun SendModelHandler(
    sendModelState: SendQuizAnswer,
    snackbarHostState: SnackbarHostState,
    onSuccess: (AddQuizInitialModel) -> Unit,
    clearState: () -> Unit
) {
    val descriptionIsTooShortMessage = stringResource(id = R.string.description_is_too_short)
    val imageIsNotSelectedMessage = stringResource(id = R.string.image_is_not_selected)
    val titleIsTooShortMessage = stringResource(id = R.string.title_is_too_short)
    when (sendModelState) {
        SendQuizAnswer.DescriptionIsTooShort -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar(descriptionIsTooShortMessage)
                clearState()
            }
        }

        SendQuizAnswer.Empty -> {}

        SendQuizAnswer.ImageIsNotSelected -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar(imageIsNotSelectedMessage)
                clearState()
            }
        }

        is SendQuizAnswer.Success -> {
            onSuccess(sendModelState.quiz)
            clearState()
        }

        SendQuizAnswer.TitleIsTooShort -> {
            LaunchedEffect(sendModelState) {
                snackbarHostState.showSnackbar(titleIsTooShortMessage)
                clearState()
            }
        }
    }
}












