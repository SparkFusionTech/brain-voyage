package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory

import com.sparkfusion.quiz.brainvoyage.domain.play_worker.NextTryCounter
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import java.time.LocalDateTime

interface VictoryQuizContract {

    sealed interface VictoryQuizIntent : Intent {
        data class InitVictoryScreen(val state: InitialState) : VictoryQuizIntent
    }

    data class InitialState(
        val quizId: Long,
        val questionsCount: Int,
        val correctAnswersCount: Int,
        val xpCount: Int,
        val nextTryAt: LocalDateTime = NextTryCounter().countNextTryCount()
    )
}















