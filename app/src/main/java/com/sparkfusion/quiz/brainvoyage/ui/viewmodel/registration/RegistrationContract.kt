package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import com.sparkfusion.quiz.brainvoyage.utils.Intent
import com.sparkfusion.quiz.brainvoyage.utils.UIState

interface RegistrationContract {

    sealed interface RegistrationIntent : Intent {

        data object Register : RegistrationIntent
        data object ChangePasswordVisibility: RegistrationIntent

        data class ChangeEmail(private val _value: String) : RegistrationIntent {
            val value: String get() = _value
        }

        data class ChangePassword(private val _value: String) : RegistrationIntent {
            val value: String get() = _value
        }
    }

    data class RegistrationUIState(
        val email: String = "",
        val password: String = "",
        val showPassword: Boolean = false
    ) : UIState
}