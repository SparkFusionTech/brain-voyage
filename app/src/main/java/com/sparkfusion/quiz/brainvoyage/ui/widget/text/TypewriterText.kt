package com.sparkfusion.quiz.brainvoyage.ui.widget.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.text.splitToCodePoints
import kotlinx.coroutines.delay

@Composable
fun TypewriterText(
    texts: List<String>,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    style: TextStyle = LocalTextStyle.current,
) {
    var textIndex by remember { mutableIntStateOf(0) }
    var textToDisplay by remember { mutableStateOf("") }
    val textCharsList: List<List<String>> = remember { texts.map { it.splitToCodePoints() } }

    LaunchedEffect(key1 = texts) {
        while (textIndex < textCharsList.size) {
            textCharsList[textIndex].forEachIndexed { charIndex, _ ->
                textToDisplay = textCharsList[textIndex]
                    .take(charIndex + 1)
                    .joinToString("")
                delay(30L)
            }

            if (++textIndex == texts.size) break else delay(1000)
        }
    }

    SFProRoundedText(
        content = textToDisplay,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        textAlign = textAlign
    )
}









