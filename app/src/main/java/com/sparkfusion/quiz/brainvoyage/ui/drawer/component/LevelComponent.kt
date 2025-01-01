package com.sparkfusion.quiz.brainvoyage.ui.drawer.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogExperienceModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun LevelComponent(
    modifier: Modifier = Modifier,
    catalogExperienceModel: CatalogExperienceModel
) {
    val color = Color(catalogExperienceModel.color.toColorInt())
    val fillPercentage = (
            catalogExperienceModel.currentXp.toFloat() / catalogExperienceModel.levelXp.toFloat()
            ).coerceIn(0f, 1f)
    val textSize = when (catalogExperienceModel.level.toString().length) {
        1, 2 -> 16.sp
        3 -> 14.sp
        else -> 11.sp
    }

    Box(
        modifier = modifier
            .padding(4.dp)
            .size(36.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color.LightGray,
                        Color.LightGray.copy(0.1f)
                    ),
                ),
                radius = size.minDimension / 2,
                style = Stroke(width = 4.dp.toPx())
            )

            drawArc(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        color, color.copy(alpha = 0.95f)
                    ),
                ),
                startAngle = -90f,
                sweepAngle = fillPercentage * 360f,
                useCenter = false,
                style = Stroke(width = 4.dp.toPx()),
                size = size
            )
        }

        SFProRoundedText(
            content = "${catalogExperienceModel.level}",
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            color = color,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.wrapContentSize(),
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        color, color.copy(alpha = 0.95f)
                    )
                )
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LevelComponentPreview() {
    LevelComponent(
        catalogExperienceModel = CatalogExperienceModel(
            id = 1L,
            currentXp = 20,
            levelXp = 80,
            name = "Baby",
            level = 78,
            color = "#FF0000FF"
        )
    )
}
















