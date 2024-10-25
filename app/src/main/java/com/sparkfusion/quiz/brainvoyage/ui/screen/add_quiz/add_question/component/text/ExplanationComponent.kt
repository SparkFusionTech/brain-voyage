package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DescriptionText

@Composable
fun ExplanationComponent(
    description: String,
    onValueChange: (String) -> Unit
) {
    SFProRoundedText(
        modifier = Modifier.padding(start = 24.dp, top = 20.dp),
        content = "Explanation",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    DefaultTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(120.dp),
        value = description,
        onValueChange = onValueChange,
        placeholder = "Enter here..."
    )

    DescriptionText(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
        content = "Add an explanation of the correct answer or just interesting information on the topic of the question*"
    )
}
