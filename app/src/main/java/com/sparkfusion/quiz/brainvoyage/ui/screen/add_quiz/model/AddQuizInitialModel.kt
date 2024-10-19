package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddQuizInitialModel(
    val bitmap: Bitmap,
    val title: String,
    val description: String,
    val tags: List<String>
) : Parcelable
