package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R

@Composable
fun ImageSelectionCards(
    modifier: Modifier = Modifier,
    selectedItem: SelectImageType,
    onItemClick: (SelectImageType) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ImageSelectionItem(
            content = "Search",
            showRadioButton = selectedItem == SelectImageType.SEARCH,
            icon = painterResource(id = R.drawable.image_selection_search_icon),
            onItemClick = { onItemClick(SelectImageType.SEARCH) }
        )

        ImageSelectionItem(
            content = "Gallery",
            showRadioButton = selectedItem == SelectImageType.GALLERY,
            icon = painterResource(id = R.drawable.image_selection_gallery_icon),
            onItemClick = { onItemClick(SelectImageType.GALLERY) }
        )
    }
}
