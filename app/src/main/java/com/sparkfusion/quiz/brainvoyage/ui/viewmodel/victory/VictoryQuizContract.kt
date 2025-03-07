package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory

import com.sparkfusion.quiz.brainvoyage.domain.play_worker.NextTryCounter
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import java.time.LocalDateTime

interface VictoryQuizContract {

    sealed interface VictoryQuizIntent : Intent {
        data class InitVictoryScreen(val state: InitialState) : VictoryQuizIntent
        data object ReadAccountInfo : VictoryQuizIntent
        data class ReadUserQuizRating(val quizId: Long) : VictoryQuizIntent
        data class UpdateRating(val rating: Int) : VictoryQuizIntent
        data object ClearRatingUpdatingState : VictoryQuizIntent
    }

    data class InitialState(
        val quizId: Long,
        val questionsCount: Int,
        val correctAnswersCount: Int,
        val xpCount: Int,
        val nextTryAt: LocalDateTime = NextTryCounter().countNextTryCount()
    )
}















