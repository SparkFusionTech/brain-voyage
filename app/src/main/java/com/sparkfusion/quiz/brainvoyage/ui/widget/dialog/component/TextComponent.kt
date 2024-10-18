package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.item.ImageSelectionCards
import com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.item.SelectImageType

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    selectedItem: SelectImageType,
    onItemClick: (SelectImageType) -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        SFProRoundedText(
            content = "Where do you want to get the image from?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        ImageSelectionCards(
            selectedItem = selectedItem,
            onItemClick = onItemClick
        )
    }
}
