package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

import com.sparkfusion.quiz.brainvoyage.utils.Intent
import com.sparkfusion.quiz.brainvoyage.utils.UIState

interface LoginContract {

    sealed interface LoginIntent : Intent {

        data object Login : LoginIntent

        class ChangeEmail(private val _email: String) : LoginIntent {
            val email: String get() = _email
        }

        class ChangePassword(private val _password: String) : LoginIntent {
            val password: String get() = _password
        }
    }

    data class LoginUIState(
        val isLoginInProcess: Boolean = false,
        val email: String = "",
        val password: String = ""
    ) : UIState
}