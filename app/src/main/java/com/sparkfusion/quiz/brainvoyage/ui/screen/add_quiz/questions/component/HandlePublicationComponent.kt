package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract

@Composable
fun HandlePublicationComponent(
    publishState: AddQuizWithQuestionsContract.PublishQuizState,
    snackbarHostState: SnackbarHostState,
    clearPublishState: () -> Unit
) {
    LaunchedEffect(publishState) {
        when (publishState) {
            AddQuizWithQuestionsContract.PublishQuizState.Empty -> {}
            AddQuizWithQuestionsContract.PublishQuizState.NotEnoughQuestions -> {
                snackbarHostState.showSnackbar("Not enough questions (min 5)")
                clearPublishState()
            }

            AddQuizWithQuestionsContract.PublishQuizState.QuizIsNull -> {
                snackbarHostState.showSnackbar("Quiz information was not found")
                clearPublishState()
            }

            AddQuizWithQuestionsContract.PublishQuizState.Success -> {
                snackbarHostState.showSnackbar("Success")
            }
        }
    }
}























