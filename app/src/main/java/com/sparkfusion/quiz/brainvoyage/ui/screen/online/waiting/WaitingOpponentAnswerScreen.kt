package com.sparkfusion.quiz.brainvoyage.ui.screen.online.waiting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.domain.model.online.OnlineGameEvent
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.games.BackIcon
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions.BouncingDots
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.Online2VS2GameViewModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.dp.getStatusBarHeightInDp
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner
import kotlinx.coroutines.delay

@Composable
fun WaitingOpponentAnswerScreen(
    modifier: Modifier = Modifier,
    viewModel: Online2VS2GameViewModel,
    timeLeft: Long,
    onBackClick: () -> Unit,
    onFinishGame: () -> Unit
) {
    val event by viewModel.event.collectAsStateWithLifecycle(initialValue = null)

    if (event == OnlineGameEvent.WaitingNextQuestion) onBackClick()

    BackHandler {
        onFinishGame()
    }

    if (event is OnlineGameEvent.WaitingOpponentAnswer) {
        val opponent = (event as OnlineGameEvent.WaitingOpponentAnswer).opponent
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
                .padding(top = if (StatusBarHeightOwner.hasCutout) getStatusBarHeightInDp().dp else 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackIcon(
                modifier = Modifier.align(Alignment.Start)
            ) {
                onFinishGame()
            }

            AsyncImage(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .align(Alignment.CenterHorizontally)
                    .size(156.dp)
                    .clip(RoundedCornerShape(24.dp)),
                model = opponent.iconUrl,
                contentDescription = null
            )

            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 12.dp),
                content = opponent.name,
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 24.sp
            )

            SFProRoundedText(
                modifier = Modifier
                    .padding(top = 4.dp),
                content = "Answering",
                color = Color(0xFFFCD71D),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.weight(1f))

            BouncingDots(color = Color(0xFFBABD03))

            Spacer(modifier = Modifier.weight(1f))

            CountdownTimer(timeLeftValue = timeLeft)
        }
    }
}

@Composable
private fun CountdownTimer(timeLeftValue: Long) {
    var timeLeft by remember { mutableLongStateOf(timeLeftValue) }
    val isFinished = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1
        }
        isFinished.value = true
    }

    SFProRoundedText(
        modifier = Modifier.padding(bottom = 48.dp),
        content = "$timeLeft seconds left",
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun WaitingOpponentAnswerScreenPreview() {
    WaitingOpponentAnswerScreen(
        viewModel = hiltViewModel(),
        timeLeft = 20,
        onBackClick = {},
        onFinishGame = {}
    )
}
























