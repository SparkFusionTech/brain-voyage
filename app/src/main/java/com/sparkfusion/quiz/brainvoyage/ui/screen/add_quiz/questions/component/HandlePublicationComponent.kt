package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract

@Composable
fun HandlePublicationComponent(
    publishState: AddQuizWithQuestionsContract.QuizVerificationState,
    snackbarHostState: SnackbarHostState,
    clearPublishState: () -> Unit
) {
    val verificationIsNullMessage = stringResource(id = R.string.quiz_information_was_not_found)
    val notEnoughQuestions = stringResource(id = R.string.not_enough_questions_min_5)
    LaunchedEffect(publishState) {
        when (publishState) {
            AddQuizWithQuestionsContract.QuizVerificationState.Empty -> {}
            AddQuizWithQuestionsContract.QuizVerificationState.NotEnoughQuestions -> {
                snackbarHostState.showSnackbar(notEnoughQuestions)
                clearPublishState()
            }

            AddQuizWithQuestionsContract.QuizVerificationState.QuizVerificationIsNull -> {
                snackbarHostState.showSnackbar(verificationIsNullMessage)
                clearPublishState()
            }
        }
    }
}























