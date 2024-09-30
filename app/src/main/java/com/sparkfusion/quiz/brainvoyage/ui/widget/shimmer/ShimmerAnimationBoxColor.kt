package com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer

import androidx.compose.ui.graphics.Color
import com.sparkfusion.quiz.brainvoyage.ui.theme.surfaceContainerHighestDark

open class ShimmerAnimationBoxColor(
    val lightModeColor: Color,
    val darkModeColor: Color
)

data object DefaultShimmerAnimationBoxColor : ShimmerAnimationBoxColor(
    lightModeColor = Color.LightGray,
    darkModeColor = surfaceContainerHighestDark
)
