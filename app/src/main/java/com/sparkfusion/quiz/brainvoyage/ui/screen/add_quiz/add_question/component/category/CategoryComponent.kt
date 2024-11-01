package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
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
    val anchor = remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp)
            .background(registrationTextFieldColor, RoundedCornerShape(12.dp))
            .onGloballyPositioned { layoutCoordinates ->
                boxSize.value = layoutCoordinates.size.toSize()
                anchor.value =
                    layoutCoordinates.positionInRoot()
            }
            .clickable { onListClick(!isListVisible) }
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .size(40.dp),
                model = categories[currentCategoryId].iconId,
                contentDescription = stringResource(id = R.string.category_icon_description)
            )

            SFProRoundedText(
                content = stringResource(id = categories[currentCategoryId].name),
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
                    contentDescription = stringResource(id = R.string.collapse_or_expand_category_list_icon_description)
                )
            }
        }
    }

    val dropdownMenuWidth = LocalDensity.current.run { boxSize.value.width.toDp() }
    DropdownMenu(
        expanded = isListVisible,
        onDismissRequest = { onListClick(false) },
        offset = LocalDensity.current.run {
            DpOffset(
                x = anchor.value.x.toDp(),
                y = (anchor.value.y + boxSize.value.height).toDp() + 56.dp
            )
        },
        modifier = Modifier.sizeIn(minWidth = dropdownMenuWidth)
    ) {
        categories.forEachIndexed { index, item ->
            DropdownMenuItem(
                text = { SFProRoundedText(content = stringResource(id = item.name)) },
                onClick = {
                    onListClick(false)
                    onItemClick(index)
                }
            )
        }
    }
}






