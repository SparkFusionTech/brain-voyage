package com.sparkfusion.quiz.brainvoyage.domain.play_worker

import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty
import javax.inject.Inject

class XpCounter @Inject constructor() {

    fun countXpForQuestion(difficulty: QuestionDifficulty): Int {
        return DEFAULT_XP_VALUE * difficulty.mapToInt()
    }

    companion object {
        private const val DEFAULT_XP_VALUE = 5
    }
}