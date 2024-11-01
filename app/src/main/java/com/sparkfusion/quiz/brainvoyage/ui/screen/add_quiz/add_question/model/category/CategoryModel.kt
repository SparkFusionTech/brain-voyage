package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sparkfusion.quiz.brainvoyage.R

data class CategoryModel(
    @DrawableRes val iconId: Int,
    @StringRes val name: Int,
    val type: CategoryType
)

fun initCategories(): Array<CategoryModel> {
    return arrayOf(
        CategoryModel(R.drawable.quiz_category_icon, R.string.quiz, CategoryType.Quiz),
        CategoryModel(R.drawable.true_false_category_icon, R.string.true_false, CategoryType.TrueFalse),
        CategoryModel(R.drawable.multiply_choice_category_icon, R.string.multiply_choice, CategoryType.MultiplyChoice)
    )
}
