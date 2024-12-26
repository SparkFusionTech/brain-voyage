package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.add_tag

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.item.DialogValueComponent
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.CancelButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.ConfirmButton
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component.TitleComponent

@Composable
fun AddTagDialog(
    show: Boolean,
    items: List<String>,
    onAddTag: (String) -> Unit,
    onDeleteTag: (Int) -> Unit,
    onDismiss: () -> Unit
) {
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
                    title = "Keywords",
                    icon = painterResource(id = R.drawable.keywords_icon)
                )
            },
            text = {
                DialogValueComponent(
                    items = items,
                    onAddItem = onAddTag,
                    onDeleteItem =onDeleteTag
                )
            },
            dismissButton = {
                CancelButton(onDismiss = onDismiss)
            },
            confirmButton = {
                ConfirmButton {
                    onDismiss()
                }
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddTagDialogPreview() {
    AddTagDialog(
        show = true,
        onDismiss = {},
        items = listOf(""),
        onAddTag = {},
        onDeleteTag = {}
    )
}






























