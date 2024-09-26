package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface LoginContract {

    sealed interface LoginIntent : Intent {
        data object Login : LoginIntent
        data class HandleRegistrationData(val pair: Pair<String?, String?>) : LoginIntent
        data class ChangeEmail(val email: String) : LoginIntent
        data class ChangePassword(val password: String) : LoginIntent
    }

    data class LoginUIState(
        val isLoginInProcess: Boolean = false,
        val email: String = "",
        val password: String = ""
    ) : UIState
}