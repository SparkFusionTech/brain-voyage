package com.sparkfusion.quiz.brainvoyage.domain.play_worker

import java.time.LocalDateTime
import javax.inject.Inject

class NextTryCounter @Inject constructor() {

    fun countNextTryCount(): LocalDateTime {
        val currentDateTime = LocalDateTime.now()
        return currentDateTime.plusHours(NEXT_TRY_PERIOD)
    }

    companion object {
        private const val NEXT_TRY_PERIOD = 6L
    }
}
