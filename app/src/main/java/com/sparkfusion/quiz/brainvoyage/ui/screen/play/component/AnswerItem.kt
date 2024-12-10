package com.sparkfusion.quiz.brainvoyage.ui.screen.play.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.PlayAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun AnswerItem(
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    answer: PlayAnswerModel,
    answerCheckState: PlayQuizContract.AnswerCheckState
) {
    val (targetBackgroundColor, targetBorderColor) = when (answerCheckState) {
        PlayQuizContract.AnswerCheckState.Empty -> Color(0xFFEAE0C8) to MaterialTheme.colorScheme.secondaryContainer
        is PlayQuizContract.AnswerCheckState.AnswerCheckResult -> when {
            answerCheckState.correctAnswersIds.contains(answer.id) -> Color.Green to Color(
                0xFF00BB00
            )

            answerCheckState.incorrectAnswersIds.contains(answer.id) -> Color.Red to Color(
                0xFFBB0000
            )

            else -> Color(0xFFEAE0C8) to MaterialTheme.colorScheme.secondaryContainer
        }
    }

    val backgroundColor by animateColorAsState(
        targetValue = targetBackgroundColor,
        label = "",
        animationSpec = tween(durationMillis = 800)
    )
    val borderColor by animateColorAsState(targetValue = targetBorderColor, label = "")

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 6.dp, bottom = 6.dp),
        onClick = {
            if (answerCheckState == PlayQuizContract.AnswerCheckState.Empty) onItemClick()
        },
        shape = RoundedCornerShape(16.dp),
        border = if (answer.isSelected) BorderStroke(4.dp, borderColor) else null
    ) {
        SFProRoundedText(
            modifier = Modifier.padding(
                vertical = 12.dp,
                horizontal = 20.dp
            ),
            content = answer.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AnswerItemPreview() {
    AnswerItem(
        onItemClick = {},
        answer = PlayAnswerModel(
            1, "name", 1, correct = false, isSelected = false
        ),
        answerCheckState = PlayQuizContract.AnswerCheckState.AnswerCheckResult(
            correctAnswersIds = emptyList(),
            incorrectAnswersIds = emptyList(),
            isCorrect = true
        )
    )
}




















