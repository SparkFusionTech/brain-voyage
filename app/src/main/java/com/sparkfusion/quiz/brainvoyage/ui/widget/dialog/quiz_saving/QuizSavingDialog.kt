package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.quiz_saving

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent

@Composable
fun QuizSavingDialog(
    show: Boolean,
    quizSavingState: AddQuizWithQuestionsContract.QuizSavingState,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    clearPublicationState: () -> Unit
) {
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loadingMessage by remember { mutableStateOf("Quiz saving...") }

    LaunchedEffect(showError) {
        if (showError) {
            snackbarHostState.showSnackbar(errorMessage)
            clearPublicationState()
        }
    }

    if (show) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            ),
            onDismissRequest = onDismiss,
            title = {
                TitleComponent(
                    title = "Publication",
                    icon = painterResource(id = R.drawable.publication_icon)
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SFProRoundedText(
                        content = "The quiz is being saved, please do not close the screen to avoid losing data!",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    when (quizSavingState) {
                        AddQuizWithQuestionsContract.QuizSavingState.Empty -> {}
                        AddQuizWithQuestionsContract.QuizSavingState.QuestionsSaving -> {
                            loadingMessage = "Questions saving..."
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.QuizSaving -> {
                            loadingMessage = "Quiz saving..."
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.Success -> {
                            onSuccess()
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.TagsSaving -> {
                            loadingMessage = "Tags saving..."
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.QuestionsSavingError -> {
                            errorMessage = "Failed save questions"
                            showError = true
                            onDismiss()
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.QuizSavingError -> {
                            errorMessage = "Failed save quiz"
                            showError = true
                            onDismiss()
                        }
                        AddQuizWithQuestionsContract.QuizSavingState.TagsSavingError -> {
                            errorMessage = "Failed save tags"
                            showError = true
                            onDismiss()
                        }
                    }

                    SFProRoundedText(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .align(Alignment.CenterHorizontally),
                        content = loadingMessage,
                        color = MaterialTheme.colorScheme.primary
                    )

                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 8.dp)
                    )
                }
            },
            confirmButton = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizSavingDialogPreview() {
    QuizSavingDialog(
        show = true,
        quizSavingState = AddQuizWithQuestionsContract.QuizSavingState.QuizSaving,
        snackbarHostState = SnackbarHostState(),
        onSuccess = {},
        onDismiss = {},
        clearPublicationState = {}
    )
}

























