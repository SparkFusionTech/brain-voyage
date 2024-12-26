package com.sparkfusion.quiz.brainvoyage.ui.dialog.quiz_saving

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.CommonBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.OwlMessageComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.progress.MovingSquaresProgress

@Composable
fun QuizSavingBottomSheet(
    show: Boolean,
    quizSavingState: AddQuizWithQuestionsContract.QuizSavingState,
    snackbarHostState: SnackbarHostState,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    clearPublicationState: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = {}
    ) {
        QuizSavingContent(
            show = show,
            quizSavingState = quizSavingState,
            snackbarHostState = snackbarHostState,
            onDismiss = onDismiss,
            onSuccess = onSuccess,
            clearPublicationState = clearPublicationState
        )
    }
}

@Composable
private fun QuizSavingContent(
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

    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.saving_quiz_owl,
        messages = listOf("It seems the larger the image, the longer it will take to save")
    )

    SFProRoundedText(
        modifier = Modifier.padding(top = 8.dp),
        content = "The quiz is being saved, please do not close the screen to avoid losing data!",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        MovingSquaresProgress(text = loadingMessage)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun QuizSavingBottomSheetPreview() {
    QuizSavingBottomSheet(
        show = true,
        quizSavingState = AddQuizWithQuestionsContract.QuizSavingState.QuizSaving,
        snackbarHostState = SnackbarHostState(),
        onSuccess = {},
        onDismiss = {},
        clearPublicationState = {}
    )
}
















