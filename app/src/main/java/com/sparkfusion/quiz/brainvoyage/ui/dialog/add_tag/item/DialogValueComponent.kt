package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun DialogValueComponent(
    modifier: Modifier = Modifier,
    items: List<String>,
    onAddItem: (String) -> Unit,
    onDeleteItem: (Int) -> Unit
) {
    var enterValue by remember { mutableStateOf("") }
    var showError by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        InputTagComponent(
            modifier = Modifier.fillMaxWidth(),
            value = enterValue,
            onValueChange = { enterValue = it },
            onButtonClick = {
                if (items.size > 10) {
                    errorMessage = "Can be max 10 tags"
                    showError = true
                } else if (enterValue.length < 3) {
                    errorMessage = "Value is too short (min 3)"
                    showError = true
                } else {
                    showError = false
                    onAddItem(enterValue)
                    enterValue = ""
                }
            }
        )

        if (showError) {
            SFProRoundedText(
                content = errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyVerticalStaggeredGrid(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .heightIn(max = 120.dp),
            columns = StaggeredGridCells.Adaptive(80.dp)
        ) {
            items(items.size) { index ->
                TagItemComponent(
                    content = items[index],
                    onItemClick = { onDeleteItem(index) }
                )
            }
        }
    }
}
