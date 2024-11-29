package com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class StatusInfoModel(
    val color: Color,
    val text: String,

    @DrawableRes
    val icon: Int
)
