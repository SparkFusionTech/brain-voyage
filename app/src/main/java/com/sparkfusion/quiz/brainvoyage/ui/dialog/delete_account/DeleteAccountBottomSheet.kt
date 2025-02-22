package com.sparkfusion.quiz.brainvoyage.ui.dialog.delete_account

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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

@Composable
fun DeleteAccountBottomSheet(
    show: Boolean,
    onDeleteAccount: (String) -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        DeleteAccountContent(
            show = show,
            onDeleteAccount = onDeleteAccount,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun DeleteAccountContent(
    show: Boolean,
    onDeleteAccount: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newPassword by remember { mutableStateOf("") }

    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.tag_owl,
        messages = listOf("Enter your password to confirm the action!")
    )

    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp),
        value = newPassword,
        onValueChange = { newPassword = it },
        placeholder = "Enter password here..."
    )

    Spacer(modifier = Modifier.height(14.dp))

    BottomSheetButton(
        onClick = {
            onDeleteAccount(newPassword)
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
private fun DeleteAccountBottomSheetPreview() {
    DeleteAccountBottomSheet(
        show = true,
        onDismiss = {},
        onDeleteAccount = {}
    )
}


















