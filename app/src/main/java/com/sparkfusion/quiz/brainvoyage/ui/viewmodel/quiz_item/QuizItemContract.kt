package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_item

import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import java.time.Duration
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent as CommonIntent

interface QuizItemContract {

    sealed interface Intent : CommonIntent {
        data class ReadQuiz(val quizId: Long) : Intent
    }

    sealed interface QuizReadingState : UIState {
        data object Loading : QuizReadingState
        data object Error : QuizReadingState
        data class Success(
            val quiz: GetQuizPreviewModel,
            val nextTryAt: Duration?,
            val rating: QuizRatingModel?
        ) : QuizReadingState
    }
}


















