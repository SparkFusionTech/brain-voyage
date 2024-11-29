package com.sparkfusion.quiz.brainvoyage.domain.model.quiz

enum class QuizStatusModel(val value: Int) {
    MODERATION(0),
    MODIFY(1),
    DENIED(-1),
    APPROVED(2);

    companion object {

        @JvmStatic
        fun fromValue(value: Int): QuizStatusModel {
            return entries.find { it.value == value } ?: MODERATION
        }
    }
}
