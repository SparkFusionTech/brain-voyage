package com.sparkfusion.quiz.brainvoyage.ui.dialog.close_game

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

@Composable
fun CloseGameBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        CloseGameContent(
            show = show,
            onConfirm = onConfirm,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun CloseGameContent(
    show: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    var isConfirmed by remember { mutableStateOf(false) }

    LaunchedEffect(show) {
        if (!show && isConfirmed) {
            onConfirm()
            isConfirmed = false
        }
    }

    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.select_image_owl,
        messages = listOf(
            "If you exit now, all your current progress and achievements will be lost!",
            "Are you sure you want to exit without saving?"
        )
    )

    Spacer(modifier = Modifier.height(8.dp))

    BottomSheetButton(
        onClick = {
            isConfirmed = true
            onDismiss()
        },
        text = "Continue",
        textColor = Color(0xFFFFDCA0),
        containerColor = buttonSuccessLightColor
    )

    BottomSheetButton(
        onClick = {
            onDismiss()
        },
        text = "Close",
        textColor = Color(0xFFFFAF80),
        containerColor = buttonCloseLightColor
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CloseGameBottomSheetPreview() {
    CloseGameBottomSheet(
        show = true,
        onDismiss = { },
        onConfirm = { }
    )
}

















