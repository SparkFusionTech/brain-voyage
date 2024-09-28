package com.sparkfusion.quiz.brainvoyage.window.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.theme.arcoFamily

@Composable
fun AnimatedSplashText(
    modifier: Modifier = Modifier,
    isTextVisible: Boolean
) {
    val appName = stringResource(id = R.string.app_name)
    appName.forEachIndexed { index, char ->
        val animatedOffsetX by animateFloatAsState(
            targetValue = if (isTextVisible) 0f else 50f,
            animationSpec = tween(durationMillis = 500, delayMillis = index * 20),
            label = stringResource(id = R.string.splash_screen_text_x_offset)
        )

        val animatedAlpha by animateFloatAsState(
            targetValue = if (isTextVisible) 1f else 0f,
            animationSpec = tween(durationMillis = 500, delayMillis = index * 20),
            label = stringResource(id = R.string.splash_screen_text_alpha)
        )

        Text(
            modifier = modifier
                .offset { IntOffset(x = animatedOffsetX.toInt(), y = 0) }
                .alpha(animatedAlpha),
            text = char.toString(),
            fontSize = 32.sp,
            fontFamily = arcoFamily,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        darkSplashTextGradientColor,
                        lightSplashTextGradientColor
                    )
                ),
                shadow = Shadow(
                    color = lightSplashTextGradientColor,
                    offset = Offset(4f, 8f),
                    blurRadius = 3f
                )
            )
        )
    }
}
