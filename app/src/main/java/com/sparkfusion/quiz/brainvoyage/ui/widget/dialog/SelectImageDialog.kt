package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TextComponent
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.item.SelectImageType

@Composable
fun SelectImageDialog(
    show: Boolean,
    onSearchNavigate: () -> Unit,
    onGalleryNavigate: () -> Unit,
    onDismiss: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(SelectImageType.SEARCH) }
    if (show) {
        AlertDialog(
            modifier = Modifier.padding(horizontal = 24.dp),
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismiss,
            title = {
                TitleComponent()
            },
            text = {
                TextComponent(
                    selectedItem = selectedItem,
                    onItemClick = { selectedItem = it })
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    SFProRoundedText(
                        content = "Cancel",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (selectedItem == SelectImageType.SEARCH) onSearchNavigate()
                        else onGalleryNavigate()
                        onDismiss()
                    }
                ) {
                    SFProRoundedText(
                        content = "Confirm",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectImageDialogPreview() {
    SelectImageDialog(
        show = true,
        onDismiss = {},
        onSearchNavigate = {},
        onGalleryNavigate = {}
    )
}
