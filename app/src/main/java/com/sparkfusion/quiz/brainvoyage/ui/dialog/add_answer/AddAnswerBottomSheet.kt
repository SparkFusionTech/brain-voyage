package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_answer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.BottomSheetButton
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.CommonBottomSheet
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.OwlMessageComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonCloseLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonSuccessLightColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText

@Composable
fun AddAnswerBottomSheet(
    show: Boolean,
    snackbarHostState: SnackbarHostState,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        AddAnswerContent(
            snackbarHostState = snackbarHostState,
            show = show,
            onDismiss = onDismiss,
            onConfirm = onConfirm
        )
    }
}

@Composable
private fun AddAnswerContent(
    snackbarHostState: SnackbarHostState,
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
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

    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.add_answer_owl,
        messages = listOf("The shorter the answer, the easier it is to choose.")
    )

    DefaultTextField(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        value = answer,
        onValueChange = {
            if (answer.length > 64) showTooLongSnackbar = true else answer = it
        },
        placeholder = "Enter here..."
    )

    DescriptionText(content = "The answer must be very brief (max 64)*")

    Spacer(modifier = Modifier.height(8.dp))

    BottomSheetButton(
        onClick = {
            if (answer.isNotEmpty()) {
                onConfirm(answer)
                answer = ""
                onDismiss()
            } else {
                showIsEmptySnackbar = true
            }
        },
        text = "Continue",
        textColor = Color(0xFFFFDCA0),
        containerColor = buttonSuccessLightColor
    )

    BottomSheetButton(
        onClick = {
            answer = ""
            onDismiss()
        },
        text = "Close",
        textColor = Color(0xFFFFAF80),
        containerColor = buttonCloseLightColor
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddAnswerBottomSheetPreview() {
    AddAnswerBottomSheet(
        show = true,
        snackbarHostState = SnackbarHostState(),
        onConfirm = {},
        onDismiss = {}
    )
}

















