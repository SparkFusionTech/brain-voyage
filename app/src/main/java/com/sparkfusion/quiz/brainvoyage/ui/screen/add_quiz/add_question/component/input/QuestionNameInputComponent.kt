package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.DefaultTextField

@Composable
fun QuestionNameInputComponent(
    value: String,
    onValueChange: (String) -> Unit
) {
    SFProRoundedText(
        modifier = Modifier.padding(start = 24.dp, top = 40.dp),
        content = "Question",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    DefaultTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        placeholder = "Enter here..."
    )
}