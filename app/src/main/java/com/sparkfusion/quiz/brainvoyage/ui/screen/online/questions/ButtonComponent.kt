package com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.AnswerCheckState
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    answerCheckResult: AnswerCheckState,
    onAnswerButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(settingsBackgroundDarkColor.copy(alpha = 0.95f))
    ) {
        AnimatedVisibility(visible = answerCheckResult is AnswerCheckState.Answered) {
            if (answerCheckResult is AnswerCheckState.Answered) {
                ResultMessage(answerCheckResult)
            }
        }

        ActionButton(
            answerCheckResult = answerCheckResult,
            onAnswerButtonClick = onAnswerButtonClick,
            onNextButtonClick = onNextButtonClick
        )
    }
}

@Composable
private fun ResultMessage(answerCheckResult: AnswerCheckState.Answered) {
    val correctMessages = stringArrayResource(id = R.array.correct_answers_messages)
    val incorrectMessages = stringArrayResource(id = R.array.incorrect_answers_messages)
    val randomMessage = remember(answerCheckResult.isCorrect) {
        if (answerCheckResult.isCorrect) correctMessages.random()
        else incorrectMessages.random()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(32.dp),
            model = if (answerCheckResult.isCorrect) R.drawable.check_mark_icon else R.drawable.cancel_icon,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        SFProRoundedText(
            modifier = Modifier.padding(horizontal = 12.dp),
            content = randomMessage,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (answerCheckResult.isCorrect) Color.Green else Color.Red
        )
    }
}

@Composable
private fun ColumnScope.ActionButton(
    answerCheckResult: AnswerCheckState,
    onAnswerButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    var clicked by remember { mutableStateOf(false) }
    val (buttonText, onClickHandler) = when (answerCheckResult) {
        is AnswerCheckState.Answered -> "Next" to onNextButtonClick
        AnswerCheckState.Empty -> "Answer" to onAnswerButtonClick
    }

    if (answerCheckResult == AnswerCheckState.Empty) {
        clicked = false
    }

    AnimatedVisibility(visible = clicked) {
        SFProRoundedText(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .padding(end = 24.dp),
            content = "Waiting for opponent action!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.LightGray,
            textAlign = TextAlign.End
        )
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = if (clicked) 4.dp else 8.dp)
            .height(56.dp)
            .padding(horizontal = 48.dp)
            .align(Alignment.CenterHorizontally)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(buttonLightColor, buttonDarkColor),
                ),
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = {
            if (!clicked) {
                onClickHandler()
                if (answerCheckResult is AnswerCheckState.Answered) {
                    clicked = true
                }
            }
        }
    ) {
        if (clicked) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            SFProRoundedText(
                content = buttonText,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}























