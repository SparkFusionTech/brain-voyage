package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun getTrueFalseCategoryAnswers(): SnapshotStateList<QuestionAnswerModel> {
    return mutableStateListOf(
        QuestionAnswerModel("True", false),
        QuestionAnswerModel("False", false)
    )
}
