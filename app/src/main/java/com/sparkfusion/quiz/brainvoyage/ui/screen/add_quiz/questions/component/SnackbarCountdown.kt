package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CustomSnackbarWithCountdown(
    message: String,
    timerProgress: Float,
    secondsRemaining: Int,
    onDismiss: () -> Unit,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black.copy(alpha = 0.8f), shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SnackbarCountdown(timerProgress, secondsRemaining, color)

            Spacer(modifier = Modifier.width(8.dp))

            SFProRoundedText(
                content = message,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = { onDismiss() }
            ) {
                SFProRoundedText(
                    content = stringResource(id = R.string.cancel),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun SnackbarCountdown(
    timerProgress: Float,
    secondsRemaining: Int,
    color: Color
) {
    Box(
        modifier = Modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(Modifier.matchParentSize()) {
            val strokeStyle = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )

            drawCircle(
                color = color.copy(alpha = 0.12f),
                style = strokeStyle
            )
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = (-360f * timerProgress),
                useCenter = false,
                style = strokeStyle
            )
        }

        SFProRoundedText(
            content = secondsRemaining.toString(),
            fontSize = 14.sp,
            color = color,
        )
    }
}
