package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.difficulty

import androidx.annotation.DrawableRes
import com.sparkfusion.quiz.brainvoyage.R
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty

data class DifficultyModel(
    @DrawableRes val iconId: Int,
    val difficulty: QuestionDifficulty
)

fun initDifficulties(): Array<DifficultyModel> {
    return arrayOf(
        DifficultyModel(R.drawable.easy_star_icon, QuestionDifficulty.Easy),
        DifficultyModel(R.drawable.normal_star_icon, QuestionDifficulty.Normal),
        DifficultyModel(R.drawable.difficult_star_icon, QuestionDifficulty.Difficult),
        DifficultyModel(R.drawable.impossible_start_icon, QuestionDifficulty.Impossible)
    )
}
