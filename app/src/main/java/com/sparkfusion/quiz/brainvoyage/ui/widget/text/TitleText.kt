package com.sparkfusion.quiz.brainvoyage.ui.widget.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    content: String
) {
    SFProRoundedText(
        modifier = modifier,
        content = content,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium,
        color = Color.White
    )
}
