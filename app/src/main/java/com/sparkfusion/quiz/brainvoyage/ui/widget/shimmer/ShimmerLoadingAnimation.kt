package com.sparkfusion.quiz.brainvoyage.ui.widget.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R

fun Modifier.shimmerLoadingAnimation(
    isLoadingCompleted: Boolean = true,
    isDarkModeEnabled: Boolean = false,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 2000,
): Modifier {
    return if (isLoadingCompleted) this else composed {
        val shimmerColors = ShimmerAnimationModeColors(isDarkModeEnabled).getColors()
        val transition = rememberInfiniteTransition(stringResource(R.string.shimmer_loading_transition))
        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = stringResource(R.string.shimmer_loading_animation)
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            )
        )
    }
}
