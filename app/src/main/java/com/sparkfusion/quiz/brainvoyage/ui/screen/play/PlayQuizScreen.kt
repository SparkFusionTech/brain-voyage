package com.sparkfusion.quiz.brainvoyage.ui.screen.play

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.component.AnswerItem
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.component.ButtonComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.component.ExplanationComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.component.TopComponent
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.close_game.CloseGamePlayingDialog

@Composable
fun PlayQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: PlayQuizViewModel = hiltViewModel(),
    victoryQuizViewModel: VictoryQuizViewModel,
    quizId: Long,
    onNavigateToVictoryScreen: () -> Unit,
    onDismiss: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.handleIntent(PlayQuizContract.PlayQuizIntent.ReadQuestions(quizId))
    }

    val currentQuestionState by viewModel.currentQuestion.collectAsStateWithLifecycle()
    val answerResult by viewModel.answerResult.collectAsStateWithLifecycle()
    val completedValueState by viewModel.completedValueState.collectAsStateWithLifecycle()
    val dialogsState by viewModel.dialogsState.collectAsStateWithLifecycle()

    BackHandler {
        viewModel.handleIntent(PlayQuizContract.PlayQuizIntent.ChangeExitDialogVisibility(true))
    }

    CloseGamePlayingDialog(
        show = dialogsState.isCloseDialogVisible,
        onDismiss = {
            viewModel.handleIntent(PlayQuizContract.PlayQuizIntent.ChangeExitDialogVisibility(false))
        },
        onConfirm = {
            onDismiss()
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.play_quiz_background),
                contentScale = ContentScale.Crop
            )
    ) {
        QuizContent(
            modifier = modifier,
            currentQuestionState = currentQuestionState,
            answerResult = answerResult,
            completedValueState = completedValueState,
            onNavigateToVictoryScreen = onNavigateToVictoryScreen,
            onAnswerButtonClick = {
                viewModel.handleIntent(PlayQuizContract.PlayQuizIntent.CheckAnswers)
            },
            onCloseButtonClick = {
                viewModel.handleIntent(
                    PlayQuizContract.PlayQuizIntent.ChangeExitDialogVisibility(true)
                )
            },
            onItemClick = { index ->
                viewModel.handleIntent(
                    PlayQuizContract.PlayQuizIntent.SelectAnswer(index)
                )
            },
            onNextButtonClick = {
                viewModel.handleIntent(
                    PlayQuizContract.PlayQuizIntent.NextQuestion(quizId)
                )
            },
            initVictoryScreen = {
                if (currentQuestionState is PlayQuizContract.CurrentQuestionState.Victory) {
                    victoryQuizViewModel.handleIntent(
                        VictoryQuizContract.VictoryQuizIntent.InitVictoryScreen(
                            (currentQuestionState as PlayQuizContract.CurrentQuestionState.Victory).victoryInitialState
                        )
                    )
                }
            }
        )
    }
}

@Composable
private fun QuizContent(
    modifier: Modifier = Modifier,
    currentQuestionState: PlayQuizContract.CurrentQuestionState,
    answerResult: PlayQuizContract.AnswerCheckState,
    completedValueState: PlayQuizContract.CompletedValueState,
    onNavigateToVictoryScreen: () -> Unit,
    onAnswerButtonClick: () -> Unit,
    onCloseButtonClick: () -> Unit,
    onItemClick: (Int) -> Unit,
    onNextButtonClick: () -> Unit,
    initVictoryScreen: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            when (currentQuestionState) {
                is PlayQuizContract.CurrentQuestionState.CurrentQuestion -> {
                    val question = currentQuestionState.question
                    item {
                        TopComponent(
                            question = question,
                            completedValueState = completedValueState,
                            onCloseButtonClick = onCloseButtonClick
                        )
                    }

                    items(question.answers.size) { index ->
                        AnswerItem(
                            onItemClick = { onItemClick(index) },
                            answer = question.answers[index],
                            answerCheckState = answerResult
                        )
                    }

                    item {
                        ExplanationComponent(
                            explanation = question.explanation,
                            answerCheckResult = answerResult
                        )
                    }
                }

                PlayQuizContract.CurrentQuestionState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                }

                is PlayQuizContract.CurrentQuestionState.Victory -> {
                    item {
                        initVictoryScreen()
                        onNavigateToVictoryScreen()
                    }
                }
            }
        }

        ButtonComponent(
            modifier = Modifier.padding(bottom = 8.dp),
            onAnswerButtonClick = onAnswerButtonClick,
            onNextButtonClick = onNextButtonClick,
            answerCheckResult = answerResult
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PlayQuizScreenPreview() {
    PlayQuizScreen(
        victoryQuizViewModel = hiltViewModel(),
        quizId = 1,
        onNavigateToVictoryScreen = {},
        onDismiss = {}
    )
}





























