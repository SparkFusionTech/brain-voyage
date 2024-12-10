package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.close_game

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.CancelButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.ConfirmButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent

@Composable
fun CloseGamePlayingDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var isConfirmed by remember { mutableStateOf(false) }

    if (show) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = onDismiss,
            title = {
                TitleComponent(
                    title = "Warning",
                    icon = painterResource(id = R.drawable.warning_icon)
                )
            },
            text = {
                SFProRoundedText(
                    content = "You haven't finished the quiz yet. If you exit now, all your current progress and achievements will be lost.\n" +
                            "\n" +
                            "Are you sure you want to exit without saving?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            dismissButton = {
                CancelButton(onDismiss = onDismiss)
            },
            confirmButton = {
                ConfirmButton {
                    isConfirmed = true
                    onDismiss()
                }
            }
        )
    }

    LaunchedEffect(show) {
        if (!show && isConfirmed) {
            onConfirm()
            isConfirmed = false
        }
    }
}