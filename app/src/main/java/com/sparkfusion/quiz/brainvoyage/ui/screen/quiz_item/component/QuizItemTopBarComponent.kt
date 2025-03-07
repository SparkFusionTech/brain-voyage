package com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.games.BackIcon

@Composable
fun QuizItemTopBarComponent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier.height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackIcon {
            onBackClick()
        }
    }
}
