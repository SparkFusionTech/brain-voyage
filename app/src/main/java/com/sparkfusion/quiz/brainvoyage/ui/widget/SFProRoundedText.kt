package com.sparkfusion.quiz.brainvoyage.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.theme.sfProRoundedFamily

@Composable
fun SFProRoundedText(
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 16.sp,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    content: String
) {
    Text(
        text = content,
        fontFamily = sfProRoundedFamily,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign
    )
}