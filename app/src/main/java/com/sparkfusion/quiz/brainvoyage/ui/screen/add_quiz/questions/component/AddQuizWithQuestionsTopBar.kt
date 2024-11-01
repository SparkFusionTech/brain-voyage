package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun AddQuizWithQuestionsTopBar(
    modifier: Modifier = Modifier,
    onCheckClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.padding(start = 8.dp),
            onClick = onBackClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_back_icon),
                contentDescription = stringResource(id = R.string.add_quiz_with_questions_back_button_icon_description)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        SFProRoundedText(
            content = stringResource(id = R.string.questions),
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onCheckClick) {
            Icon(
                painter = painterResource(id = R.drawable.round_check),
                contentDescription = stringResource(id = R.string.publish_icon_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}





















