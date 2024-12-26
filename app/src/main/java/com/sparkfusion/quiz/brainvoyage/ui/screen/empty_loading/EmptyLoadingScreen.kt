package com.sparkfusion.quiz.brainvoyage.ui.screen.empty_loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.widget.progress.MovingSquaresProgress

@Composable
fun EmptyLoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        MovingSquaresProgress(text = "Loading")
    }
}
