package com.sparkfusion.quiz.brainvoyage.ui.widget.star

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val easyStartColor = Color(0xFF00FF0A)
private val easyEndColor = Color(0xFF009906)
private val normalStartColor = Color(0xFF00E0FF)
private val normalEndColor = Color(0xFF014BB9)
private val difficultStartColor = Color(0xFFFE0707)
private val difficultEndColor = Color(0xFF990000)
private val impossibleStartColor = Color(0xFFDF0086)
private val impossibleEndColor = Color(0xFF5A0026)

sealed class QuestionDifficulty(val brush: Brush) {

    data object Easy : QuestionDifficulty(
        Brush.verticalGradient(
            colors = listOf(easyStartColor, easyEndColor)
        )
    )

    data object Normal : QuestionDifficulty(
        Brush.verticalGradient(
            colors = listOf(normalStartColor, normalEndColor)
        )
    )

    data object Difficult : QuestionDifficulty(
        Brush.verticalGradient(
            colors = listOf(difficultStartColor, difficultEndColor)
        )
    )

    data object Impossible : QuestionDifficulty(
        Brush.verticalGradient(
            colors = listOf(impossibleStartColor, impossibleEndColor)
        )
    )
}













