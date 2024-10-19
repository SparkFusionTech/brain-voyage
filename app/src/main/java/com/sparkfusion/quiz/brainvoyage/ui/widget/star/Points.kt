package com.sparkfusion.quiz.brainvoyage.ui.widget.star

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

fun DrawScope.drawStar(
    difficulty: QuestionDifficulty,
    center: Offset,
    outerRadius: Float,
    innerRadius: Float,
    points: Int,
    cornerRadius: Float
) {
    val starPoints = createStar(center, outerRadius, innerRadius, points)
    val path = Path()

    if (starPoints.isNotEmpty()) {
        path.moveTo(starPoints[0].x, starPoints[0].y)

        for (i in starPoints.indices) {
            val currentPoint = starPoints[i]
            val nextIndex = (i + 1) % starPoints.size
            val nextPoint = starPoints[nextIndex]

            val direction = Offset(
                (nextPoint.x - currentPoint.x),
                (nextPoint.y - currentPoint.y)
            ).normalize()

            val start = currentPoint + direction * cornerRadius
            val end = nextPoint - direction * cornerRadius

            path.lineTo(start.x, start.y)
            path.quadraticTo(nextPoint.x, nextPoint.y, end.x, end.y)
        }

        path.close()
    }

    drawPath(
        path = path,
        brush = difficulty.brush
    )
}

fun createStar(center: Offset, outerRadius: Float, innerRadius: Float, points: Int): List<Offset> {
    val starPoints = mutableListOf<Offset>()
    val angleIncrement = (2 * PI) / (points * 2)

    for (i in 0 until points * 2) {
        val radius = if (i % 2 == 0) outerRadius else innerRadius
        val angle = angleIncrement * i
        val x = (center.x + radius * cos(angle)).toFloat()
        val y = (center.y + radius * sin(angle)).toFloat()
        starPoints.add(Offset(x, y))
    }

    return starPoints
}

fun Offset.normalize(): Offset {
    val length = sqrt(x * x + y * y)
    return Offset(x / length, y / length)
}
