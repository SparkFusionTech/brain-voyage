package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.close_adding

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.CancelButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.ConfirmButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent

@Composable
fun CloseQuizAddingDialog(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
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
                    content = "If you exit now, all saved data will DISAPPEAR.\n" +
                            "\n" +
                            "Are you sure you want to exit?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            dismissButton = {
                CancelButton(onDismiss = onDismiss)
            },
            confirmButton = {
                ConfirmButton(onClick = onConfirm)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CloseQuizAddingDialogPreview() {
    CloseQuizAddingDialog(
        show = true,
        onDismiss = {},
        onConfirm = {}
    )
}































