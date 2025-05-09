package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.close_adding.CloseQuizAddingBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.dialog.quiz_saving.QuizSavingBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.AddQuizWithQuestionsTopBar
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.HandlePublicationComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.PreviewInformationComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component.QuestionItemComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsContract.Intent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions.AddQuizWithQuestionsViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun AddQuizWithQuestionScreen(
    modifier: Modifier = Modifier,
    viewModel: AddQuizWithQuestionsViewModel = hiltViewModel(),
    sharedQuizViewModel: SharedQuizViewModel,
    sharedQuestionsViewModel: SharedQuestionsViewModel,
    onBackClick: () -> Unit,
    onAddQuestionClick: () -> Unit,
    onCloseQuizAddingScreen: () -> Unit
) {
    val model by sharedQuizViewModel.quizModel.collectAsStateWithLifecycle()
    val questions by sharedQuestionsViewModel.questions.collectAsStateWithLifecycle()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val publishState by viewModel.quizVerificationState.collectAsStateWithLifecycle()
    val savingState by viewModel.quizAddingState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val changeCloseDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(Intent.ChangeCloseDialogVisibility(v))
    }
    val changePublicationDialogVisibility = { v: Boolean ->
        viewModel.handleIntent(Intent.ChangePublicationDialogVisibility(v))
    }
    val clearQuizVerificationState = { viewModel.handleIntent(Intent.ClearQuizVerificationState) }
    val clearSavingState = { viewModel.handleIntent(Intent.ClearSavingState) }

    BackHandler { changeCloseDialogVisibility(true) }
    HandlePublicationComponent(
        publishState = publishState,
        snackbarHostState = snackbarHostState,
        clearPublishState = clearQuizVerificationState
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp),
                hostState = snackbarHostState
            )
        },
        topBar = {
            AddQuizWithQuestionsTopBar(
                modifier = Modifier.padding(
                    top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp
                ),
                onCheckClick = {
                    viewModel.handleIntent(
                        Intent.SaveQuiz(addQuizInitialModel = model, questions = questions)
                    )
                },
                onBackClick = {
                    changeCloseDialogVisibility(true)
                }
            )
        }
    ) {
        if (model == null) CircularProgressIndicator()
        else
            LazyColumn(
                modifier = modifier
                    .background(
                        Brush.linearGradient(
                            listOf(
                                settingsBackgroundLightColor,
                                settingsBackgroundDarkColor
                            )
                        )
                    )
                    .padding(it)
                    .fillMaxSize()
            ) {
                item {
                    PreviewInformationComponent(model = model!!)
                }

                items(questions.size) { index ->
                    QuestionItemComponent(
                        model = questions[index],
                        onDeleteItem = {
                            sharedQuestionsViewModel.handleIntent(
                                SharedQuestionContract.Intent.DeleteQuestion(index)
                            )
                        }
                    )
                }

                item {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 100.dp, vertical = 20.dp)
                            .fillMaxWidth()
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(buttonLightColor, buttonDarkColor),
                                ),
                                shape = RoundedCornerShape(50.dp)
                            )
                            .clip(RoundedCornerShape(50.dp)),
                        onClick = onAddQuestionClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        SFProRoundedText(
                            modifier = Modifier.padding(vertical = 6.dp),
                            content = stringResource(id = R.string.add),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
    }

    QuizSavingBottomSheet(
        show = state.showPublicationDialog,
        quizSavingState = savingState,
        snackbarHostState = snackbarHostState,
        onDismiss = {
            changePublicationDialogVisibility(false)
            clearSavingState()
        },
        onSuccess = {
            changePublicationDialogVisibility(false)
            onCloseQuizAddingScreen()
        },
        clearPublicationState = {
            changePublicationDialogVisibility(false)
            clearSavingState()
        }
    )

    CloseQuizAddingBottomSheet(
        show = state.showCloseDialog,
        onDismiss = { changeCloseDialogVisibility(false) },
        onConfirm = { onBackClick() }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddQuizWithQuestionsScreenPreview() {
    AddQuizWithQuestionScreen(
        onBackClick = {},
        onAddQuestionClick = {},
        sharedQuizViewModel = SharedQuizViewModel(),
        sharedQuestionsViewModel = SharedQuestionsViewModel(),
        onCloseQuizAddingScreen = {}
    )
}
























