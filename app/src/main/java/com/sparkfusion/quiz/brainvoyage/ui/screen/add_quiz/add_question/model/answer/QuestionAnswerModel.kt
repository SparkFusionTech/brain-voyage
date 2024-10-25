package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionAnswerModel(
    val name: String,
    val isCorrect: Boolean
) : Parcelable
