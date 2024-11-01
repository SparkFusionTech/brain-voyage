package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

fun getTrueFalseCategoryAnswers(values: List<String>): SnapshotStateList<QuestionAnswerModel> {
    return mutableStateListOf(
        QuestionAnswerModel(values[0], false),
        QuestionAnswerModel(values[1], false)
    )
}
