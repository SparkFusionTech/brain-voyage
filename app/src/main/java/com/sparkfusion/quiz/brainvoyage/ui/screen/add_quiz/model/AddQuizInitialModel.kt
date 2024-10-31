package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model

import android.graphics.Bitmap
import android.os.Parcelable
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.AddQuizModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddQuizInitialModel(
    val bitmap: Bitmap,
    val title: String,
    val description: String,
    val tags: List<String>,
    val catalogId: Long
) : Parcelable {

    fun map(questionsCount: Int): AddQuizModel {
        return AddQuizModel(title, description, questionsCount, catalogId)
    }
}
