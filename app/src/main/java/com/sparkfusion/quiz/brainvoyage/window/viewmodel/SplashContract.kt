package com.sparkfusion.quiz.brainvoyage.window.viewmodel

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface SplashContract {

    sealed interface SplashIntent : Intent

    sealed interface SplashState : UIState {
        data object Loading : SplashState
        data object Error : SplashState
        data object Success : SplashState
    }
}