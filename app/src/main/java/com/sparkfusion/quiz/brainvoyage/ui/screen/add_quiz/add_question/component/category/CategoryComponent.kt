package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryModel
import com.sparkfusion.quiz.brainvoyage.ui.theme.registrationTextFieldColor
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun CategoryComponent(
    modifier: Modifier = Modifier,
    currentCategoryId: Int,
    categories: Array<CategoryModel>,
    onListClick: (Boolean) -> Unit,
    onItemClick: (Int) -> Unit,
    isListVisible: Boolean
) {
    val boxSize = remember { mutableStateOf(Size.Zero) }

    Row(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .background(registrationTextFieldColor, RoundedCornerShape(12.dp))
            .onGloballyPositioned { layoutCoordinates ->
                boxSize.value = layoutCoordinates.size.toSize()
            }
            .clickable { onListClick(!isListVisible) }
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .clip(RoundedCornerShape(12.dp))
                .size(40.dp),
            model = categories[currentCategoryId].iconId,
            contentDescription = null
        )

        SFProRoundedText(
            content = categories[currentCategoryId].name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.padding(end = 8.dp),
            onClick = {
                onListClick(!isListVisible)
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_arrow_drop_down),
                contentDescription = null
            )
        }
    }

    val dropdownMenuWidth = LocalDensity.current.run { boxSize.value.width.toDp() }
    DropdownMenu(
        modifier = Modifier.sizeIn(minWidth = dropdownMenuWidth),
        expanded = isListVisible,
        onDismissRequest = { onListClick(false) }
    ) {
        categories.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = {
                    SFProRoundedText(content = item.name)
                },
                onClick = {
                    onListClick(false)
                    onItemClick(index)
                }
            )
        }
    }
}





