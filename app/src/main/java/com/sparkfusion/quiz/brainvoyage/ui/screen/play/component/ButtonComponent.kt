package com.sparkfusion.quiz.brainvoyage.ui.screen.play.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    answerCheckResult: PlayQuizContract.AnswerCheckState,
    onAnswerButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(settingsBackgroundDarkColor.copy(alpha = 0.95f))
    ) {
        AnimatedVisibility(visible = answerCheckResult is PlayQuizContract.AnswerCheckState.AnswerCheckResult) {
            if (answerCheckResult is PlayQuizContract.AnswerCheckState.AnswerCheckResult) {
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
private fun ResultMessage(answerCheckResult: PlayQuizContract.AnswerCheckState.AnswerCheckResult) {
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
    answerCheckResult: PlayQuizContract.AnswerCheckState,
    onAnswerButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit
) {
    val (buttonText, onClickHandler) = when (answerCheckResult) {
        is PlayQuizContract.AnswerCheckState.AnswerCheckResult -> "Next" to onNextButtonClick
        PlayQuizContract.AnswerCheckState.Empty -> "Answer" to onAnswerButtonClick
    }

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, top = 8.dp)
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
        onClick = onClickHandler
    ) {
        SFProRoundedText(
            content = buttonText,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ButtonComponentPreview() {
    ButtonComponent(
        answerCheckResult = PlayQuizContract.AnswerCheckState.Empty,
        onAnswerButtonClick = {},
        onNextButtonClick = {}
    )
}



















