package com.sparkfusion.quiz.brainvoyage.ui.screen.victory.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.window.splash.lightSplashTextGradientColor

@Composable
fun VictoryBackgroundBlock(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val ovalWidthPx = with(LocalDensity.current) { 130.dp.toPx() }
    val ovalHeightPx = with(LocalDensity.current) { 100.dp.toPx() }
    val squareWidthPx = with(LocalDensity.current) { 300.dp.toPx() }
    val squareHeightPx = with(LocalDensity.current) { 340.dp.toPx() }

    val trophyBitmap = ImageBitmap.imageResource(id = R.drawable.win_trophy)

    val specialImageWidth1 = with(LocalDensity.current) { (130 * 0.7).dp.toPx() }
    val specialImageHeight1 = with(LocalDensity.current) { (100 * 0.8).dp.toPx() }

    val specialImageWidth2 = with(LocalDensity.current) { (130 * 0.6).dp.toPx() }
    val specialImageHeight2 = with(LocalDensity.current) { (100 * 0.7).dp.toPx() }

    val specialImageWidth3 = with(LocalDensity.current) { (130 * 0.5).dp.toPx() }
    val specialImageHeight3 = with(LocalDensity.current) { (100 * 0.6).dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.play_quiz_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        SFProRoundedText(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (LocalConfiguration.current.screenHeightDp / 6).dp),
            content = "Quiz passed!",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFFFCD71D)
                    )
                ),
                shadow = Shadow(
                    color = lightSplashTextGradientColor,
                    offset = Offset(2f, 4f),
                    blurRadius = 2f
                )
            )
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2 - ovalWidthPx / 2
            val centerY = size.height / 2 - squareWidthPx / 2

            drawOval(
                color = Color(0xFFEAE0C8),
                topLeft = Offset(centerX - 60.dp.toPx(), centerY + 20.dp.toPx()),
                size = Size(ovalWidthPx, ovalHeightPx)
            )

            val roundRectTopLeft = Offset(
                centerX + ovalWidthPx / 2 - squareWidthPx / 2,
                centerY + ovalHeightPx / 2 + 16.dp.toPx()
            )

            drawRoundRect(
                color = Color(0xFFEAE0C8),
                topLeft = roundRectTopLeft,
                size = Size(squareWidthPx, squareHeightPx),
                cornerRadius = CornerRadius(20.dp.toPx())
            )

            drawOval(
                color = Color(0xFFEAE0C8),
                topLeft = Offset(centerX, centerY),
                size = Size(ovalWidthPx, ovalHeightPx)
            )

            drawOval(
                color = Color(0xFFEAE0C8),
                topLeft = Offset(centerX + 60.dp.toPx(), centerY + 30.dp.toPx()),
                size = Size(ovalWidthPx, ovalHeightPx)
            )

            val trophyOffsets = listOf(
                Offset(
                    centerX - 94.dp.toPx() + (ovalWidthPx / 2) - (trophyBitmap.width / 2),
                    centerY + (ovalHeightPx / 2) - (trophyBitmap.height / 2)
                ),
                Offset(
                    centerX + (ovalWidthPx / 2) - (trophyBitmap.width / 2),
                    centerY + (ovalHeightPx / 2) - (trophyBitmap.height / 2) - 30
                ),
                Offset(
                    centerX + 110.dp.toPx() + (ovalWidthPx / 2) - (trophyBitmap.width / 2),
                    centerY + (ovalHeightPx / 2) - (trophyBitmap.height / 2)
                )
            )

            scale(
                specialImageWidth2 / trophyBitmap.width,
                specialImageHeight2 / trophyBitmap.height
            ) {
                drawImage(trophyBitmap, trophyOffsets[0])
            }
            scale(
                specialImageWidth1 / trophyBitmap.width,
                specialImageHeight1 / trophyBitmap.height
            ) {
                drawImage(trophyBitmap, trophyOffsets[1])
            }
            scale(
                specialImageWidth3 / trophyBitmap.width,
                specialImageHeight3 / trophyBitmap.height
            ) {
                drawImage(trophyBitmap, trophyOffsets[2])
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .width(280.dp)
                .height(276.dp)
                .offset(y = 106.dp)
                .background(Color.Transparent)
        ) {
            content()
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun VictoryBackgroundBlockPreview() {
    VictoryBackgroundBlock {
        SFProRoundedText(
            content = "Victory Content",
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}












