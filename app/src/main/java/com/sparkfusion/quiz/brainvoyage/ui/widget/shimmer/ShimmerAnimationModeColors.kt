package com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer

import androidx.compose.ui.graphics.Color

data class ShimmerAnimationModeColors(
    private val isDarkTheme: Boolean
) {

    fun getColors(): List<Color> {
        val color = if (isDarkTheme) Color.Black else Color.White
        return listOf(
            color.copy(alpha = 0.3f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 1.0f),
            color.copy(alpha = 0.5f),
            color.copy(alpha = 0.3f)
        )
    }
}
