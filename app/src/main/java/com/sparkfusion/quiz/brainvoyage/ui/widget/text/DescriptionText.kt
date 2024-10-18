package com.sparkfusion.quiz.brainvoyage.ui.widget.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun DescriptionText(
    modifier: Modifier = Modifier,
    content: String
) {
    SFProRoundedText(
        modifier = modifier,
        color = descriptionColor(),
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        content = content
    )
}
