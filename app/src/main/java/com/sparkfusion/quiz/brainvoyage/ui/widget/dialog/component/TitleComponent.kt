package com.sparkfusion.quiz.brainvoyage.ui.widget.dialog.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun TitleComponent(
    modifier: Modifier = Modifier,
    title: String,
    icon: Painter
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .height(26.dp)
                .padding(end = 10.dp),
            painter = icon,
            contentDescription = null
        )

        SFProRoundedText(
            content = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
