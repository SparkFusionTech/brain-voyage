package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.add_answer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.CancelButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.ConfirmButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText

@Composable
fun AddAnswerDialog(
    show: Boolean,
    snackbarHostState: SnackbarHostState,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var answer by remember { mutableStateOf("") }
    var showTooLongSnackbar by remember { mutableStateOf(false) }
    var showIsEmptySnackbar by remember { mutableStateOf(false) }

    if (showTooLongSnackbar) {
        LaunchedEffect(answer) {
            snackbarHostState.showSnackbar("Answer is too long")
            showTooLongSnackbar = false
        }
    }

    if (showIsEmptySnackbar) {
        LaunchedEffect(answer) {
            snackbarHostState.showSnackbar("Answer must not be empty")
            showIsEmptySnackbar = false
        }
    }

    if (show) {
        AlertDialog(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnClickOutside = false
            ),
            onDismissRequest = onDismiss,
            title = {
                TitleComponent(
                    title = "Answer",
                    icon = painterResource(id = R.drawable.answer_dialog_icon)
                )
            },
            text = {
                Column {
                    DefaultTextField(
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                        value = answer,
                        onValueChange = {
                            if (answer.length == 24) showTooLongSnackbar = true else answer = it
                        },
                        placeholder = "Enter here..."
                    )

                    DescriptionText(content = "The answer must be very brief (max 24)*")
                }
            },
            dismissButton = {
                CancelButton(onDismiss = onDismiss)
            },
            confirmButton = {
                ConfirmButton {
                    if (answer.isNotEmpty()) {
                        onConfirm(answer)
                        onDismiss()
                    } else {
                        showIsEmptySnackbar = true
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddAnswerDialogPreview() {
    AddAnswerDialog(
        show = true,
        snackbarHostState = SnackbarHostState(),
        onConfirm = {},
        onDismiss = {}
    )
}
