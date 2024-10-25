package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer.QuestionAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.theme.arcoFamily
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText
import com.sparkfusion.quiz.brainvoyage.utils.primaryGradientWithAlpha

@Composable
fun AnswerItemComponent(
    modifier: Modifier = Modifier,
    index: Int,
    categoryType: CategoryType,
    answer: QuestionAnswerModel,
    onRadioButtonClick: (Int) -> Unit,
    onCheckButtonClick: (Int, Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = primaryGradientWithAlpha(),
                shape = RoundedCornerShape(16.dp),
                alpha = 0.2f
            )
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
            fontFamily = arcoFamily,
            text = "${index + 1}",
            fontSize = 28.sp
        )

        SFProRoundedText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .width(220.dp),
            content = answer.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        when (categoryType) {
            CategoryType.MultiplyChoice -> {
                Checkbox(
                    checked = answer.isCorrect,
                    onCheckedChange = { onCheckButtonClick(index, it) }
                )
            }

            CategoryType.Quiz, CategoryType.TrueFalse -> {
                RadioButton(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    selected = answer.isCorrect,
                    onClick = { onRadioButtonClick(index) }
                )
            }
        }
    }
}
