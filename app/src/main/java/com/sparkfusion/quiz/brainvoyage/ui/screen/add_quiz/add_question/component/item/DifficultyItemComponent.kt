package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.item

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.difficulty.DifficultyModel

@Composable
fun DifficultyItemComponent(
    modifier: Modifier = Modifier,
    difficulty: DifficultyModel,
    isCurrent: Boolean,
    onItemClick: () -> Unit
) {
    Box(
        modifier = modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isCurrent) {
            Icon(
                modifier = Modifier.align(Alignment.TopEnd),
                painter = painterResource(id = R.drawable.thumbtack_icon),
                contentDescription = stringResource(id = R.string.difficulty_thumbtack_icon_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable { onItemClick() }
                .border(
                    1.dp,
                    if (isCurrent) MaterialTheme.colorScheme.primary
                    else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier.size(48.dp),
                model = difficulty.iconId,
                contentDescription = stringResource(id = R.string.difficulty_icon_description),
                contentScale = ContentScale.Fit
            )
        }
    }
}
