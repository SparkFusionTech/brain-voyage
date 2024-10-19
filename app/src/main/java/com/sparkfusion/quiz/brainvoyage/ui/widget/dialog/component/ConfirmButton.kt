package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ConfirmButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        SFProRoundedText(
            content = "Confirm",
            fontWeight = FontWeight.SemiBold
        )
    }
}
