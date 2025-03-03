package com.sparkfusion.quiz.brainvoyage.ui.screen.online.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R

@Composable
fun BackIcon(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 4.dp)
            .size(48.dp)
            .padding(start = 8.dp, top = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    Color.Black.copy(alpha = 0.4f),
                    CircleShape
                )
        )

        IconButton(
            modifier = Modifier,
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}