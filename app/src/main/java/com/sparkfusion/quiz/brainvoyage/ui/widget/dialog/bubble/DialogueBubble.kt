package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.bubble

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.theme.dialogueBubbleContainerColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.TypewriterText
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DialogueBubble(
    modifier: Modifier = Modifier,
    texts: List<String>
) {
    var boxHeight by remember { mutableIntStateOf(0) }
    val animatedHeight by animateDpAsState(targetValue = boxHeight.dp / 3, label = "")

    Row(modifier = modifier) {
        DialogueBubbleTriangle(modifier = Modifier.padding(top = animatedHeight))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp)
                .background(
                    color = dialogueBubbleContainerColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    boxHeight = layoutCoordinates.size.height
                }
        ) {
            TypewriterText(
                texts = texts,
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun DialogueBubbleTriangle(
    modifier: Modifier = Modifier,
    triangleColor: Color = dialogueBubbleContainerColor,
    width: Float = 80f,
    height: Float = 50f
) {
    val centerX = remember(width) { width / 2 }
    val centerY = remember(height) { height / 2 }

    val angleInDegrees = 10.0
    val angleInRadians = Math.toRadians(angleInDegrees).toFloat()

    val cosAngle = cos(angleInRadians)
    val sinAngle = sin(angleInRadians)
    val baseXCoordinate = remember(width) { width * cosAngle }

    Canvas(modifier = modifier) {
        val path = Path().apply {
            moveTo(baseXCoordinate, centerY + centerX * sinAngle - 60)
            lineTo(baseXCoordinate, sinAngle + 20)
            lineTo(0f, height)
            close()
        }

        drawPath(path = path, color = triangleColor)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DialogueBubblePreview() {
    MaterialTheme {
        DialogueBubble(
            texts = listOf(
                "If you exit now, all saved data will DISAPPEAR.\n" +
                        "\n" +
                        "Are you sure you want to exit?"
            )
        )
    }
}


