package com.sparkfusion.quiz.brainvoyage.ui.screen.image.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions.BouncingDots
import com.sparkfusion.quiz.brainvoyage.ui.theme.bouncingDotsLoading

@Composable
fun LoadingImageSearchScreenContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        BouncingDots(color = bouncingDotsLoading)
    }
}
