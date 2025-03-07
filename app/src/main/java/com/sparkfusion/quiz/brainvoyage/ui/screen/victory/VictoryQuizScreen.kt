package com.sparkfusion.quiz.brainvoyage.ui.screen.victory

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.rating.QuizRatingBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.victory.xpColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun VictoryQuizScreen(
    modifier: Modifier = Modifier,
    viewModel: VictoryQuizViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.initialState.collectAsStateWithLifecycle()
    val accountInfoState by viewModel.accountInfo.collectAsStateWithLifecycle()
    val ratingState by viewModel.ratingState.collectAsStateWithLifecycle()
    val updateRatingState by viewModel.updateRatingState.collectAsStateWithLifecycle()

    var showRatingDialog by remember { mutableStateOf(false) }

    BackHandler(onBack = onBackClick)

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val scale = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        if (state != null) {
            viewModel.handleIntent(VictoryQuizContract.VictoryQuizIntent.ReadUserQuizRating(state!!.quizId))
        }

        coroutineScope.launch {
            delay(200L)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 12.dp)
            )
        }
    ) { paddings ->
        when (updateRatingState) {
            VictoryQuizViewModel.UpdateRatingState.Error -> {
                LaunchedEffect(updateRatingState) {
                    snackbarHostState.showSnackbar("Error")
                    viewModel.handleIntent(VictoryQuizContract.VictoryQuizIntent.ClearRatingUpdatingState)
                }
            }

            VictoryQuizViewModel.UpdateRatingState.Initial -> {}
        }

        VictoryQuizContent(
            modifier = modifier
                .paint(
                    painter = painterResource(id = R.drawable.planets_orbiting_space_background),
                    contentScale = ContentScale.Crop
                )
                .padding(paddings),
            state = state,
            accountInfoState = accountInfoState,
            ratingState = ratingState,
            showRatingDialog = showRatingDialog,
            scale = scale.value,
            textTryTimeFormatter = viewModel::formatNextTryTime,
            onChangeRatingDialogValue = {
                showRatingDialog = it
            },
            onUpdateSelectedRating = {
                viewModel.handleIntent(VictoryQuizContract.VictoryQuizIntent.UpdateRating(it))
                showRatingDialog = false
            },
            onBackClick = onBackClick
        )
    }
}

@Composable
private fun VictoryQuizContent(
    modifier: Modifier = Modifier,
    state: VictoryQuizContract.InitialState?,
    accountInfoState: VictoryQuizViewModel.AccountInfo,
    ratingState: VictoryQuizViewModel.RatingState,
    showRatingDialog: Boolean,
    scale: Float,
    textTryTimeFormatter: (LocalDateTime) -> String,
    onChangeRatingDialogValue: (value: Boolean) -> Unit,
    onUpdateSelectedRating: (rating: Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f))
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(420.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(scale),
                painter = painterResource(id = R.drawable.victory_trophy),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 180.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    model = R.drawable.victory_account_light,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                AsyncImage(
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    model = accountInfoState.accountUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Image(
                    modifier = Modifier
                        .padding(start = 64.dp, top = 72.dp)
                        .size(36.dp),
                    painter = painterResource(id = R.drawable.medal_trophy),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier.padding(top = 180.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SFProRoundedText(
                        modifier = Modifier,
                        content = accountInfoState.name.ifEmpty { "Not Found" },
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 22.sp
                    )

                    if (state != null) {
                        SFProRoundedText(
                            modifier = Modifier,
                            content = "${state.xpCount} XP",
                            color = xpColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }

        state?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .padding(horizontal = 24.dp)
                    .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(20.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, end = 24.dp, start = 24.dp)
                    ) {
                        SFProRoundedText(
                            modifier = Modifier,
                            content = "Game time",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        SFProRoundedText(
                            modifier = Modifier,
                            content = "Not found",
                            color = xpColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp, start = 24.dp, top = 4.dp)
                    ) {
                        SFProRoundedText(
                            modifier = Modifier,
                            content = "Correct answers",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        SFProRoundedText(
                            modifier = Modifier,
                            content = "${it.correctAnswersCount}/${it.questionsCount}",
                            color = xpColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 24.dp, start = 24.dp, top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SFProRoundedText(
                            modifier = Modifier,
                            content = "Rating",
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(
                            modifier = Modifier
                                .background(Color(0xFF0066FF), RoundedCornerShape(16.dp))
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable { onChangeRatingDialogValue(true) },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.filled_star_icon),
                                contentDescription = null,
                                tint = xpColor
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            SFProRoundedText(
                                modifier = Modifier,
                                content = when (ratingState) {
                                    VictoryQuizViewModel.RatingState.Error -> "-"
                                    VictoryQuizViewModel.RatingState.Initial -> ""
                                    VictoryQuizViewModel.RatingState.Progress -> ""
                                    is VictoryQuizViewModel.RatingState.Success -> ratingState.rating?.rating?.toString()
                                        ?: "-"
                                },
                                color = xpColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                textAlign = TextAlign.End
                            )
                        }
                    }

                    SFProRoundedText(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 16.dp, top = 16.dp),
                        content = "Next try at ${textTryTimeFormatter(it.nextTryAt)}",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            modifier = Modifier.padding(bottom = 56.dp),
            onClick = onBackClick
        ) {
            SFProRoundedText(
                content = "Click to continue",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
        }
    }

    QuizRatingBottomSheet(
        show = showRatingDialog,
        initialState = if (ratingState is VictoryQuizViewModel.RatingState.Success) ratingState.rating?.rating else null,
        onDismiss = { onChangeRatingDialogValue(false) },
        onSuccess = { onUpdateSelectedRating(it) }
    )
}




















