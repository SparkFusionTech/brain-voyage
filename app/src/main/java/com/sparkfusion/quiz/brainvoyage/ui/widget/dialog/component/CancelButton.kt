package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component

import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CancelButton(
    onDismiss: () -> Unit
) {
    TextButton(onClick = onDismiss) {
        SFProRoundedText(
            content = "Cancel",
            fontWeight = FontWeight.SemiBold
        )
    }
}
