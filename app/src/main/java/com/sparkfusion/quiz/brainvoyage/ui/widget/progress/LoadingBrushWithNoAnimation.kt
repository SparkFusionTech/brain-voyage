package com.sparkfusion.quiz.brainvoyage.ui.widget.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun loadingBrush(showBrush: Boolean = true): Brush {
    return if (showBrush) {
        val brushColors = listOf(
            Color.LightGray.copy(alpha = 0.8f),
            Color.LightGray.copy(alpha = 0.5f)
        )

        Brush.linearGradient(colors = brushColors)
    } else {
        Brush.linearGradient(colors = listOf(Color.Transparent, Color.Transparent))
    }
}