package com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun InverseProgressIndicator(seconds: Long) {
    val progress = remember { Animatable(100f) }
    var isFinished by remember { mutableStateOf(false) }

    LaunchedEffect(seconds) {
        if (seconds > 0) {
            progress.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = (seconds * 1000).toInt(),
                    easing = LinearEasing
                )
            )
            isFinished = true
        }
    }

    CustomProgressBar(
        modifier = Modifier
            .clip(
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomEnd = 12.dp,
                    bottomStart = 12.dp
                )
            )
            .height(14.dp),
        width = LocalConfiguration.current.screenWidthDp.dp,
        foregroundColor = Brush.horizontalGradient(
            listOf(
                Color(0xffFD7D20),
                Color(0xffFBE41A)
            )
        ),
        percent = progress.value
    )
}

@Composable
private fun CustomProgressBar(
    modifier: Modifier,
    width: Dp,
    foregroundColor: Brush,
    percent: Float
) {
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .width(width)
    ) {
        Box(
            modifier = modifier
                .background(foregroundColor)
                .width((width * percent / 100).coerceIn(0.dp, width))
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InverseProgressIndicatorPreview() {
    InverseProgressIndicator(20)
}
























