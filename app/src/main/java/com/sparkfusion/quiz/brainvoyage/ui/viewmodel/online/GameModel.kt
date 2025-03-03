package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class GameModel(
    @StringRes val name: Int,
    @DrawableRes val drawable: Int
)
