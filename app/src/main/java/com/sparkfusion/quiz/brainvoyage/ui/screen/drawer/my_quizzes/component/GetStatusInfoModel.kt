package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.QuizStatusModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.model.StatusInfoModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.color.ModifyColor

@Composable
fun statusInfoModel(status: QuizStatusModel): StatusInfoModel {
    return when (status) {
        QuizStatusModel.MODERATION -> StatusInfoModel(
            MaterialTheme.colorScheme.secondaryContainer,
            stringResource(id = R.string.moderation),
            R.drawable.clock_icon
        )

        QuizStatusModel.MODIFY -> StatusInfoModel(
            ModifyColor,
            stringResource(id = R.string.modify),
            R.drawable.pencil_icon
        )

        QuizStatusModel.DENIED -> StatusInfoModel(
            MaterialTheme.colorScheme.error,
            stringResource(id = R.string.denied),
            R.drawable.cross_icon
        )

        QuizStatusModel.APPROVED -> StatusInfoModel(
            Color.Transparent,
            "",
            R.drawable.clock_icon
        )
    }
}