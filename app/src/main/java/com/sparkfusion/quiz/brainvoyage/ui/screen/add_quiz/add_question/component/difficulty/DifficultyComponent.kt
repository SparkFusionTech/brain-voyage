package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.difficulty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.component.item.DifficultyItemComponent
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.difficulty.DifficultyModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.SFProRoundedText

@Composable
fun DifficultyComponent(
    difficulties: Array<DifficultyModel>,
    currentDifficultyId: Int,
    onItemClick: (Int) -> Unit
) {
    SFProRoundedText(
        modifier = Modifier.padding(start = 24.dp, top = 40.dp),
        content = "Difficulty",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )

    LazyRow(
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp, top = 12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(difficulties.size) { index ->
            DifficultyItemComponent(
                difficulty = difficulties[index],
                isCurrent = currentDifficultyId == index,
                onItemClick = { onItemClick(index) }
            )
        }
    }
}




