package com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions

import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
private fun InfiniteTransition.animateDotsAnchor(
    initialStartOffset: StartOffset = StartOffset(0),
    label: String = "FloatAnimation"
): State<Float> {
    return animateValue(
        initialValue = 0f,
        targetValue = 1f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            initialStartOffset = initialStartOffset,
            repeatMode = RepeatMode.Reverse
        ),
        label = label
    )
}

@Composable
private fun InfiniteTransition.animateDotsSize(
    initialStartOffset: StartOffset = StartOffset(0),
    label: String = "FloatAnimation"
): State<Float> {
    return animateValue(
        initialValue = 24f,
        targetValue = 16f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            initialStartOffset = initialStartOffset,
            repeatMode = RepeatMode.Reverse
        ),
        label = label
    )
}

@Composable
private fun InfiniteTransition.animateDotsAlpha(
    initialStartOffset: StartOffset = StartOffset(0),
    label: String = "FloatAnimation"
): State<Float> {
    return animateValue(
        initialValue = 1f,
        targetValue = 0.6f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            initialStartOffset = initialStartOffset,
            repeatMode = RepeatMode.Reverse
        ),
        label = label
    )
}

@Composable
fun BouncingDots(
    color: Color
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val anchor1 by infiniteTransition.animateDotsAnchor()
    val alpha1 by infiniteTransition.animateDotsAlpha()
    val size1 by infiniteTransition.animateDotsSize()

    val anchor2 by infiniteTransition.animateDotsAnchor(
        initialStartOffset = StartOffset(250, StartOffsetType.Delay)
    )
    val alpha2 by infiniteTransition.animateDotsAlpha(
        initialStartOffset = StartOffset(250, StartOffsetType.Delay)
    )
    val size2 by infiniteTransition.animateDotsSize(
        initialStartOffset = StartOffset(250, StartOffsetType.Delay)
    )

    val anchor3 by infiniteTransition.animateDotsAnchor(
        initialStartOffset = StartOffset(500, StartOffsetType.Delay)
    )
    val alpha3 by infiniteTransition.animateDotsAlpha(
        initialStartOffset = StartOffset(500, StartOffsetType.Delay)
    )
    val size3 by infiniteTransition.animateDotsSize(
        initialStartOffset = StartOffset(500, StartOffsetType.Delay)
    )

    Row(
        modifier = Modifier.heightIn(min = 60.dp),
        verticalAlignment = Alignment.Top
    ) {
        Dot(progress = anchor1, alpha = alpha1, size = size1, color = color)
        Dot(progress = anchor2, alpha = alpha2, size = size2, color = color)
        Dot(progress = anchor3, alpha = alpha3, size = size3, color = color)
    }
}

@Composable
private fun Dot(progress: Float, alpha: Float, size: Float, color: Color) {
    val yOffset = with(LocalDensity.current) {
        (progress * 12.dp.toPx()).toInt()
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(size.dp)
            .offset(y = yOffset.dp)
            .background(color.copy(alpha = alpha), CircleShape)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewBouncingDots() {
    Surface {
        BouncingDots(
            color = Color.Blue
        )
    }
}










