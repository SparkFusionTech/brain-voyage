package com.sparkfusion.quiz.brainvoyage.ui.dialog.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.bubble.DialogueBubble
import kotlinx.coroutines.delay

@Composable
fun OwlMessageComponent(
    show: Boolean,
    @DrawableRes owlImageId: Int,
    messages: List<String>
) {
    var showOwl by remember { mutableStateOf(false) }
    var showTail by remember { mutableStateOf(false) }

    LaunchedEffect(show) {
        if (show) {
            delay(250)
            showOwl = true
        } else showOwl = false
    }

    LaunchedEffect(showOwl) {
        if (showOwl) {
            delay(200)
            showTail = true
        } else {
            showTail = false
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        AnimatedVisibility(visible = showOwl) {
            Image(
                modifier = Modifier.size(132.dp),
                painter = painterResource(id = owlImageId),
                contentDescription = null
            )
        }

        AnimatedVisibility(visible = showTail) {
            DialogueBubble(
                modifier = Modifier.zIndex(1f),
                texts = messages
            )
        }
    }
}














