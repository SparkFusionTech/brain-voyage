package com.sparkfusion.quiz.brainvoyage.ui.widget.star

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun StarCanvas(
    modifier: Modifier = Modifier,
    sizeDp: Dp,
    cornerRadiusDp: Dp,
    difficulty: QuestionDifficulty
) {
    val sizePx = with(LocalDensity.current) { sizeDp.toPx() }
    val cornerRadiusPx = with(LocalDensity.current) { cornerRadiusDp.toPx() }

    Canvas(modifier = modifier.size(sizeDp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val outerRadius = sizePx / 2
        val innerRadius = outerRadius / 2
        val points = 5

        drawStar(difficulty, center, outerRadius, innerRadius, points, cornerRadiusPx)
    }
}
