package com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection.GameInitializingComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.AnswerCheckState
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.Online2VS2GameViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun OnlineQuestionScreen(
    modifier: Modifier = Modifier,
    viewModel: Online2VS2GameViewModel,
    onVictoryScreenNavigate: () -> Unit,
    onWaitingOpponentScreenNavigate: () -> Unit,
    onFinishGame: () -> Unit
) {
    val events by viewModel.event.collectAsStateWithLifecycle(null)
    val question by viewModel.question.collectAsStateWithLifecycle()
    val answerResult by viewModel.answerResult.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    BackHandler {
        onFinishGame()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            settingsBackgroundLightColor,
                            settingsBackgroundDarkColor
                        )
                    )
                )
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                events is OnlineGameEvent.GameStart ||
                        events == OnlineGameEvent.WaitingNextQuestion ||
                        events is OnlineGameEvent.NextQuestion ||
                        events == OnlineGameEvent.AnswerTimeout -> {
                    Log.d("TAGTAG", events.toString())
                    Content(
                        questionState = question,
                        answerResult = answerResult,
                        timeLeft = viewModel.answerTimeLeft,
                        onUpdateSelectedAnswer = {
                            viewModel.updateSelectedAnswer(it)
                        },
                        onCloseButtonClick = {
                            onFinishGame()
                        },
                        onAnswerButtonClick = {
                            viewModel.answerQuestion()
                        },
                        onNextButtonClick = {
                            viewModel.nextQuestion()
                        }
                    )
                }
            }

            when (events) {
                OnlineGameEvent.PlayerDisconnected -> {
                    onFinishGame()
                }

                is OnlineGameEvent.OpponentDisconnected -> {
                    viewModel.updateReason(
                        Online2VS2GameViewModel.VictoryReason.OpponentDisconnected(
                            (events as OnlineGameEvent.OpponentDisconnected).player
                        )
                    )
                    onVictoryScreenNavigate()
                }

                is OnlineGameEvent.GameEnd -> {
                    viewModel.updateReason(
                        Online2VS2GameViewModel.VictoryReason.GameEnd(
                            (events as OnlineGameEvent.GameEnd).players
                        )
                    )
                    onVictoryScreenNavigate()
                }

                is OnlineGameEvent.WaitingOpponentAnswer -> {
                    onWaitingOpponentScreenNavigate()
                }

                OnlineGameEvent.Unexpected -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar("Unexpected error")
                    }
                }

                OnlineGameEvent.PlayerNotInGame -> {
                    ConnectionLostComponent(
                        message = "Connection was lost",
                        onBackClick = onFinishGame
                    )
                }

                OnlineGameEvent.GameError -> {
                    ConnectionLostComponent(
                        message = "Error game creating",
                        onBackClick = onFinishGame
                    )
                }

                is OnlineGameEvent.Error -> {
                    LaunchedEffect(snackbarHostState) {
                        snackbarHostState.showSnackbar("Network error")
                    }
                }

                else -> {
                    GameInitializingComponent(onCancelClick = onFinishGame)
                }
            }
        }
    }
}

@Composable
fun TopComponent(
    modifier: Modifier = Modifier,
    question: QuestionWithAnswersModel,
    answerCheckState: AnswerCheckState,
    timeLeft: Long,
    onCloseButtonClick: () -> Unit
) {
    Column {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(color = Color.Transparent)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp)),
                model = question.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            this@Column.AnimatedVisibility(visible = answerCheckState == AnswerCheckState.Empty) {
                InverseProgressIndicator(seconds = timeLeft - 1)
            }

            Box(
                modifier = Modifier
                    .padding(top = 24.dp, end = 12.dp)
                    .clip(CircleShape)
                    .size(44.dp)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = onCloseButtonClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.exit_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .clip(RoundedCornerShape(16.dp, 16.dp, 20.dp, 20.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                SFProRoundedText(
                    modifier = Modifier.padding(
                        vertical = 12.dp,
                        horizontal = 20.dp
                    ),
                    content = question.name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Content(
    questionState: Online2VS2GameViewModel.QuestionState,
    answerResult: AnswerCheckState,
    timeLeft: Long,
    onUpdateSelectedAnswer: (Int) -> Unit,
    onCloseButtonClick: () -> Unit,
    onAnswerButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    when (questionState) {
        Online2VS2GameViewModel.QuestionState.Empty -> {}
        is Online2VS2GameViewModel.QuestionState.Question -> {
            val question = questionState.question
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    item {
                        TopComponent(
                            question = question,
                            timeLeft = timeLeft,
                            answerCheckState = answerResult,
                            onCloseButtonClick = onCloseButtonClick
                        )
                    }

                    items(question.answers.size) { index ->
                        AnswerItem(
                            onItemClick = {
                                onUpdateSelectedAnswer(index)
                            },
                            answer = question.answers[index],
                            answerCheckState = answerResult
                        )
                    }
                }

                ButtonComponent(
                    onAnswerButtonClick = onAnswerButtonClick,
                    answerCheckResult = answerResult,
                    onNextButtonClick = onNextButtonClick
                )
            }
        }
    }
}
























