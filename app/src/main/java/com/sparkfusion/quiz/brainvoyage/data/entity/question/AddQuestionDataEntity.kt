package com.sparkfusion.quiz.brainvoyage.data.entity.question

import com.google.gson.annotations.SerializedName
import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AddAnswerDataEntity

data class AddQuestionDataEntity(

    @SerializedName("name")
    val name: String,

    @SerializedName("category")
    val category: Int,

    @SerializedName("difficulty")
    val difficulty: Int,

    @SerializedName("explanation")
    val explanation: String,

    @SerializedName("quizId")
    val quizId: Long,

    @SerializedName("answers")
    val answers: List<AddAnswerDataEntity>
)
