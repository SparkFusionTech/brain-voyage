package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.select_image.item

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ImageSelectionItem(
    modifier: Modifier = Modifier,
    showRadioButton: Boolean,
    icon: Painter,
    content: String,
    onItemClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(40.dp)
            .width(140.dp),
        onClick = onItemClick,
        shape = RoundedCornerShape(16.dp),
        border = if (showRadioButton) BorderStroke(1.dp, MaterialTheme.colorScheme.primary) else null
    ) {
        Row(
            modifier = Modifier
                .height(40.dp)
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.height(26.dp),
                painter = icon,
                contentDescription = null
            )

            SFProRoundedText(
                modifier = Modifier.padding(start = 4.dp),
                content = content
            )

            if (showRadioButton) {
                Spacer(modifier = Modifier.weight(1f))
                RadioButton(selected = true, onClick = null)
            }
        }
    }
}
