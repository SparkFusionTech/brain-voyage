package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class CategoryType : Parcelable {
    @Parcelize data object Quiz : CategoryType()
    @Parcelize data object MultiplyChoice : CategoryType()
    @Parcelize data object TrueFalse : CategoryType()
}
