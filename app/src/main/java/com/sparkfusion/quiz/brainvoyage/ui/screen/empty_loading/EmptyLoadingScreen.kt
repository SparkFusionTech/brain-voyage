package com.sparkfusion.quiz.brainvoyage.ui.screen.empty_loading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.widget.progress.MovingSquaresProgress

@Composable
fun EmptyLoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        MovingSquaresProgress(text = "Loading")
        Spacer(modifier = Modifier.height(24.dp))
    }
}
