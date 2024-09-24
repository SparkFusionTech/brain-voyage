package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import android.graphics.Bitmap
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface RegistrationContract {

    sealed interface RegistrationIntent : Intent {
        data object Register : RegistrationIntent
        data object ChangePasswordVisibility : RegistrationIntent
        data class ChangeAccountIcon(val value: Bitmap?) : RegistrationIntent
        data class ChangeEmail(val value: String) : RegistrationIntent
        data class ChangePassword(val value: String) : RegistrationIntent
    }

    data class RegistrationUIState(
        val email: String = "",
        val password: String = "",
        val showPassword: Boolean = false,
        val accountIcon: Bitmap? = null,
        val registrationState: RegistrationState = RegistrationState.Empty
    ) : UIState
}