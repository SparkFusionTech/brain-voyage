package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

sealed interface LoginState {
    data object Empty : LoginState
    data object Loading : LoginState
    data object Error : LoginState
    data object NetworkError : LoginState
    data object Success : LoginState
}