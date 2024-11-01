package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.StarCanvas
import com.sparkfusion.quiz.brainvoyage.utils.descriptionColor
import com.sparkfusion.quiz.brainvoyage.utils.primaryGradientWithAlpha

@Composable
fun QuestionItemComponent(
    modifier: Modifier = Modifier,
    model: SendQuestionModel
) {
    Row(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 2.dp, bottom = 4.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(20.dp)
            )
            .background(
                brush = primaryGradientWithAlpha(),
                shape = RoundedCornerShape(20.dp),
                alpha = 0.1f
            )
            .height(96.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(24.dp)
                .clip(RoundedCornerShape(16.dp))
                .size(48.dp),
            model = model.icon,
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(id = R.string.question_item_image_description)
        )

        Column(
            modifier = Modifier.padding(),
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                SFProRoundedText(
                    content = model.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                StarCanvas(
                    modifier = Modifier.padding(start = 4.dp),
                    sizeDp = 28.dp,
                    cornerRadiusDp = 2.dp,
                    difficulty = model.difficulty
                )
            }

            SFProRoundedText(
                content = stringResource(id = R.string.answer_options_template, model.answers.size),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = descriptionColor()
            )
        }
    }
}




























