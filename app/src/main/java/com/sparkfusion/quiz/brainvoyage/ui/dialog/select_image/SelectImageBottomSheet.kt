package com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image

import androidx.compose.foundation.layout.Spacer
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
import com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image.item.SelectImageType
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonCloseLightColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.buttonSuccessLightColor

@Composable
fun SelectImageBottomSheet(
    show: Boolean,
    onSearchNavigate: () -> Unit,
    onGalleryNavigate: () -> Unit,
    onDismiss: () -> Unit
) {
    CommonBottomSheet(
        show = show,
        onDismiss = onDismiss
    ) {
        SelectImageContent(
            show = show,
            onSearchNavigate = onSearchNavigate,
            onGalleryNavigate = onGalleryNavigate,
            onDismiss = onDismiss
        )
    }
}

@Composable
private fun SelectImageContent(
    show: Boolean,
    onSearchNavigate: () -> Unit,
    onGalleryNavigate: () -> Unit,
    onDismiss: () -> Unit
) {
    var selectedItem by remember { mutableStateOf(SelectImageType.SEARCH) }

    OwlMessageComponent(
        show = show,
        owlImageId = R.drawable.warning_owl,
        messages = listOf("Search for the desired picture by keywords.")
    )

    TextComponent(
        modifier = Modifier.padding(top = 8.dp),
        selectedItem = selectedItem,
        onItemClick = { selectedItem = it }
    )

    Spacer(modifier = Modifier.height(14.dp))

    BottomSheetButton(
        onClick = {
            if (selectedItem == SelectImageType.SEARCH) onSearchNavigate()
            else onGalleryNavigate()
            onDismiss()
        },
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SelectImageBottomSheetPreview() {
    SelectImageBottomSheet(
        show = true,
        onDismiss = {},
        onSearchNavigate = {},
        onGalleryNavigate = {}
    )
}















