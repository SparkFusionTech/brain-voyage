package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CategoryType : Parcelable {
    @Parcelize data object Quiz : CategoryType()
    @Parcelize data object MultiplyChoice : CategoryType()
    @Parcelize data object TrueFalse : CategoryType()

    fun mapToInt(): Int = when(this) {
        Quiz -> 1
        MultiplyChoice -> 2
        TrueFalse -> 3
    }

    companion object {

        @JvmStatic
        fun fromInt(value: Int): CategoryType? {
            return when (value) {
                1 -> Quiz
                2 -> MultiplyChoice
                3 -> TrueFalse
                else -> null
            }
        }
    }
}
