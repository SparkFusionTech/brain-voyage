package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.text

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
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
        content = stringResource(id = R.string.explanation),
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.White
    )

    DefaultTextField(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth()
            .height(120.dp),
        value = description,
        onValueChange = onValueChange,
        placeholder = stringResource(id = R.string.enter_here)
    )

    DescriptionText(
        modifier = Modifier.padding(start = 24.dp, end = 24.dp),
        content = stringResource(id = R.string.add_an_explanation_of_the_correct_answer)
    )
}














