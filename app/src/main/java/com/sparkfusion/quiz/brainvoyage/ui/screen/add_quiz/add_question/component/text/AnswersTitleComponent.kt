package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.text

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.text.TitleText
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor

@Composable
fun AnswersTitleComponent(
    modifier: Modifier = Modifier,
    subtitle: String
) {
    Row(
        modifier = modifier.padding(start = 24.dp, end = 24.dp, top = 30.dp)
    ) {
        TitleText(content = stringResource(id = R.string.answers))

        SFProRoundedText(
            modifier = Modifier.padding(start = 4.dp),
            content = subtitle,
            fontWeight = FontWeight.Medium,
            color = descriptionColor()
        )
    }
}
