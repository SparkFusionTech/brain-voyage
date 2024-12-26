package com.sparkfusion.quiz.brainvoyage.ui.dialog.add_tag.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun TagItemComponent(
    modifier: Modifier = Modifier,
    content: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 2.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(end = 4.dp, start = 4.dp)
                .clip(CircleShape)
                .clickable(onClick = onItemClick),
            painter = painterResource(id = R.drawable.round_close),
            contentDescription = null
        )

        SFProRoundedText(
            modifier = Modifier.wrapContentWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            content = content,
            color = descriptionColor()
        )
    }
}























