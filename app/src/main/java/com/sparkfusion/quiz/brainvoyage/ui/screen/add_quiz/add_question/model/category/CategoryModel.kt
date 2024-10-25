package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category

import androidx.annotation.DrawableRes
import com.sparkfusion.quiz.brainvoyage.R

data class CategoryModel(
    @DrawableRes val iconId: Int,
    val name: String,
    val type: CategoryType
)

fun initCategories(): Array<CategoryModel> {
    return arrayOf(
        CategoryModel(R.drawable.quiz_category_icon, "Quiz", CategoryType.Quiz),
        CategoryModel(R.drawable.true_false_category_icon, "True/False", CategoryType.TrueFalse),
        CategoryModel(R.drawable.multiply_choice_category_icon, "Multiply choice", CategoryType.MultiplyChoice)
    )
}
