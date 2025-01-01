package com.sparkfusion.quiz.brainvoyage.ui.widget.progress

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun MovingSquaresProgress(
    text: String,
    grid: Int = 3,
    squareSize: Dp = 32.dp,
    cornerRadius: Dp = 8.dp
) {
    val squareSizeFloat = with(LocalDensity.current) { squareSize.toPx() }
    val animatedSquareSize = Size(squareSizeFloat * 1.2f, squareSizeFloat * 1.2f)

    val cornerRadiusFloat = with(LocalDensity.current) { cornerRadius.toPx() }

    val moveDirections = listOf(
        Offset(0f, -squareSizeFloat),
        Offset(0f, squareSizeFloat),
        Offset(-squareSizeFloat, 0f),
        Offset(squareSizeFloat, 0f)
    )

    var dotCount by remember { mutableIntStateOf(0) }

    val animatedX1 = remember { Animatable(squareSizeFloat) }
    val animatedY1 = remember { Animatable(squareSizeFloat) }

    val animatedX2 = remember { Animatable(squareSizeFloat) }
    val animatedY2 = remember { Animatable(squareSizeFloat) }

    val animatedX3 = remember { Animatable(squareSizeFloat) }
    val animatedY3 = remember { Animatable(squareSizeFloat) }

    val color1 = remember { Animatable(Color(0xFF001BA3)) }
    val color2 = remember { Animatable(Color(0xFF004B8D)) }
    val color3 = remember { Animatable(Color(0xFF0623E4)) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            color1.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
            color1.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            color2.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
            color2.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000L)
            color3.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
            color3.animateTo(randomBlueColor(), animationSpec = tween(durationMillis = 1000))
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            dotCount = (dotCount + 1) % 4
        }
    }

    LaunchedEffect(Unit) {
        launch { animateSquare(moveDirections, squareSizeFloat, grid, animatedX1, animatedY1) }
        launch { animateSquare(moveDirections, squareSizeFloat, grid, animatedX2, animatedY2) }
        launch { animateSquare(moveDirections, squareSizeFloat, grid, animatedX3, animatedY3) }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 2 - squareSize * grid - 40.dp

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(screenHeight))
        Canvas(modifier = Modifier.size(squareSize * grid)) {
            drawRoundRect(
                color = color1.targetValue,
                topLeft = Offset(animatedX1.value, animatedY1.value),
                size = animatedSquareSize,
                cornerRadius = CornerRadius(cornerRadiusFloat, cornerRadiusFloat)
            )

            drawRoundRect(
                color = color2.targetValue,
                topLeft = Offset(animatedX2.value, animatedY2.value),
                size = animatedSquareSize,
                cornerRadius = CornerRadius(cornerRadiusFloat, cornerRadiusFloat)
            )

            drawRoundRect(
                color = color3.targetValue,
                topLeft = Offset(animatedX3.value, animatedY3.value),
                size = animatedSquareSize,
                cornerRadius = CornerRadius(cornerRadiusFloat, cornerRadiusFloat)
            )
        }

        Spacer(modifier = Modifier.size(screenHeight))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            SFProRoundedText(
                content = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = listOf(buttonDarkColor, buttonLightColor),
                        start = Offset(40f, 0f)
                    ),
                    shadow = Shadow(
                        color = buttonDarkColor,
                        offset = Offset(2f, 3f),
                        blurRadius = 1f
                    )
                )
            )

            (0 until 3).forEach { index ->
                val isVisible = index < dotCount
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300))
                ) {
                    SFProRoundedText(
                        content = " •",
                        fontSize = 24.sp,
                        modifier = Modifier,
                        style = TextStyle(
                            brush = Brush.linearGradient(
                                colors = listOf(buttonDarkColor, buttonLightColor)
                            )
                        )
                    )
                }
            }
        }
    }
}

private fun randomBlueColor(): Color {
    return Color(
        red = Random.nextInt(0, 20),
        green = Random.nextInt(20, 120),
        blue = Random.nextInt(120, 256)
    )
}

private suspend fun animateSquare(
    moveDirections: List<Offset>,
    squareSize: Float,
    grid: Int,
    animatedX: Animatable<Float, AnimationVector1D>,
    animatedY: Animatable<Float, AnimationVector1D>
) = coroutineScope {
    while (true) {
        val randomDirection = moveDirections[Random.nextInt(moveDirections.size)]
        val newX = animatedX.value + randomDirection.x
        val newY = animatedY.value + randomDirection.y

        val canMoveX = (newX >= 0 && newX <= (grid - 1) * squareSize)
        val canMoveY = (newY >= 0 && newY <= (grid - 1) * squareSize)

        if (canMoveX && canMoveY) {
            launch {
                animatedX.animateTo(
                    targetValue = newX,
                    animationSpec = TweenSpec(durationMillis = 500)
                )
                animatedY.animateTo(
                    targetValue = newY,
                    animationSpec = TweenSpec(durationMillis = 500)
                )
            }
            delay(Random.nextLong(500L, 1000L))
        } else {
            delay(Random.nextLong(150L, 300L))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Preview() {
    MaterialTheme {
        MovingSquaresProgress(
            text = "Loading"
        )
    }
}