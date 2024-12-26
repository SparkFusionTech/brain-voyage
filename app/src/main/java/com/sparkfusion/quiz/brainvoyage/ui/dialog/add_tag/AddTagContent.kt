package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.item.DialogValueComponent
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.BottomSheetButton
import com.sparkfusion.quiz.brainvoyage.ui.dialog.common.OwlMessageComponent
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonCloseLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonSuccessLightColor

@Composable
fun AddTagContent(
    show: Boolean,
    items: List<String>,
    onAddTag: (String) -> Unit,
    onDeleteTag: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.tag_owl,
        messages = listOf("Hi, friend!\nEnter words to help to find your quiz \uD83D\uDE0B")
    )

    DialogValueComponent(
        modifier = Modifier.padding(top = 6.dp),
        items = items,
        onAddItem = onAddTag,
        onDeleteItem =onDeleteTag
    )

    Spacer(modifier = Modifier.height(14.dp))

    BottomSheetButton(
        onClick = onDismiss,
        text = "Continue",
        textColor = Color(0xFFFFDCA0),
        containerColor = buttonSuccessLightColor
    )

    BottomSheetButton(
        onClick = onDismiss,
        text = "Close",
        textColor = Color(0xFFFFAF80),
        containerColor = buttonCloseLightColor
    )
}






















