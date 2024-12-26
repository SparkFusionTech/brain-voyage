package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.select_image

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
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
import com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image.TextComponent
import com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image.item.SelectImageType

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
                TitleComponent(
                    title = "Image Selection",
                    icon = painterResource(id = R.drawable.image_selection_dialog_icon)
                )
            },
            text = {
                TextComponent(
                    selectedItem = selectedItem,
                    onItemClick = { selectedItem = it })
            },
            dismissButton = {
                CancelButton(onDismiss = onDismiss)
            },
            confirmButton = {
                ConfirmButton {
                    if (selectedItem == SelectImageType.SEARCH) onSearchNavigate()
                    else onGalleryNavigate()
                    onDismiss()
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
