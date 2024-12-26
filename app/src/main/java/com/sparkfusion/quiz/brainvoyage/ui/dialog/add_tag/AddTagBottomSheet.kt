package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.CommonBottomSheet

@Composable
fun AddTagBottomSheet(
    show: Boolean,
    items: List<String>,
    onAddTag: (String) -> Unit,
    onDeleteTag: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        AddTagContent(
            show = show,
            items = items,
            onAddTag = onAddTag,
            onDeleteTag = onDeleteTag,
            onDismiss = onDismiss
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddTagBottomSheetPreview() {
    AddTagBottomSheet(
        show = true,
        onDismiss = {},
        items = listOf(""),
        onAddTag = {},
        onDeleteTag = {}
    )
}
















