package com.sparkfusion.quiz.brainvoyage.ui.dialog.select_image.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ImageSelectionItem(
            modifier = Modifier.weight(1f),
            content = "Search",
            showRadioButton = selectedItem == SelectImageType.SEARCH,
            icon = painterResource(id = R.drawable.image_selection_search_icon),
            onItemClick = { onItemClick(SelectImageType.SEARCH) }
        )

        Spacer(modifier = Modifier.width(8.dp))

        ImageSelectionItem(
            modifier = Modifier.weight(1f),
            content = "Gallery",
            showRadioButton = selectedItem == SelectImageType.GALLERY,
            icon = painterResource(id = R.drawable.image_selection_gallery_icon),
            onItemClick = { onItemClick(SelectImageType.GALLERY) }
        )
    }
}
