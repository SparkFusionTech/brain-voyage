package com.sparkfusion.quiz.brainvoyage.ui.screen.play.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.play.PlayQuizContract
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun ExplanationComponent(
    modifier: Modifier = Modifier,
    explanation: String,
    answerCheckResult: PlayQuizContract.AnswerCheckState,
) {
    val isVisibility = remember(answerCheckResult) {
        answerCheckResult is PlayQuizContract.AnswerCheckState.AnswerCheckResult && explanation.isNotEmpty()
    }

    AnimatedVisibility(visible = isVisibility) {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 8.dp)
        ) {
            SFProRoundedText(
                modifier = Modifier,
                content = "Explanation",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            SFProRoundedText(
                modifier = Modifier.padding(top = 6.dp),
                content = explanation,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
    }
}
















