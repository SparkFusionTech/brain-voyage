package com.sparkfusion.quiz.brainvoyage.ui.screen.empty_loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions.BouncingDots
import com.sparkfusion.quiz.brainvoyage.ui.theme.bouncingDotsLoading
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun EmptyLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SFProRoundedText(
            modifier = Modifier.padding(bottom = 8.dp),
            content = "Loading...",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(
                        bouncingDotsLoading.copy(0.6f),
                        bouncingDotsLoading
                    )
                )
            )
        )
        BouncingDots(color = bouncingDotsLoading)
//        Spacer(modifier = Modifier.weight(1f))
//        MovingSquaresProgress(text = "Loading...")
//        Spacer(modifier = Modifier.height(24.dp))
    }
}
