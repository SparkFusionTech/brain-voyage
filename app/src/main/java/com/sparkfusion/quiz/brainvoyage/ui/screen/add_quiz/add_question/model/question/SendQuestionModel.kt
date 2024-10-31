package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question

import android.graphics.Bitmap
import android.os.Parcelable
import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer.QuestionAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendQuestionModel(
    val name: String,
    val icon: Bitmap,
    val categoryType: CategoryType,
    val explanation: String,
    val difficulty: QuestionDifficulty,
    val answers: List<QuestionAnswerModel>
) : Parcelable {

    fun map(quizId: Long): AddQuestionModel {
        return AddQuestionModel(
            name,
            categoryType.mapToInt(),
            difficulty.mapToInt(),
            explanation,
            quizId,
            answers.mapIndexed { index, answerModel ->
                answerModel.map(index + 1)
            }
        )
    }
}
