package com.sparkfusion.quiz.brainvoyage.ui.widget.progress

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonLightColor
import kotlinx.coroutines.delay
import kotlin.random.Random

/** 0 - right
 *  1 - down
 *  2 - left
 *  3 - up
 * */
@Composable
fun Progress(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val gridSize = 2
    val totalSquares = gridSize * gridSize

    val spacing = with(LocalDensity.current) { 4.dp.toPx() }
    val cornerRadius = with(LocalDensity.current) { 6.dp.toPx() }
    val squareSize = with(LocalDensity.current) {
        (size.toPx() - spacing * (gridSize - 1)) / gridSize
    }

    var occupied by remember { mutableStateOf(List(totalSquares) { false }) }
    var positions by remember { mutableStateOf(List(totalSquares) { it }) }
    val animatedColors = remember { List(totalSquares) { Animatable(Color.Transparent) } }

    LaunchedEffect(Unit) {
        while (true) {
            positions = positions.map { position ->
                val direction = Random.nextInt(0, 4)
                val currentX = position % gridSize
                val currentY = position / gridSize
                when (direction) {
                    0 -> if (currentX < gridSize - 1) position + 1 else position
                    1 -> if (currentY < gridSize - 1) position + gridSize else position
                    2 -> if (currentX > 0) position - 1 else position
                    3 -> if (currentY > 0) position - gridSize else position
                    else -> position
                }
            }

            occupied = List(totalSquares) { false }
            positions.forEach { pos ->
                occupied = occupied.toMutableList().apply { this[pos] = true }
            }

            for (i in 0 until totalSquares) {
                delay(Random.nextLong(80L, 150L))
                animatedColors[i].animateTo(
                    if (occupied[i]) buttonLightColor else Color.White,
                    animationSpec = tween(durationMillis = Random.nextInt(80, 120))
                )
            }
        }
    }

    Canvas(modifier = modifier.size(size)) {
        for (i in 0 until totalSquares) {
            val x = (i % gridSize) * (squareSize + spacing)
            val y = (i / gridSize) * (squareSize + spacing)

            val color = animatedColors[i].value
            val gradientBrush = Brush.verticalGradient(
                colors = listOf(
                    color, if (color == Color.White) Color.Gray else buttonDarkColor
                )
            )

            drawRoundRect(
                brush = gradientBrush,
                topLeft = Offset(x, y),
                size = Size(squareSize, squareSize),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun ProgressPreview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Progress()
    }
}

















