package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel

sealed interface RegistrationState {
    data object Loading : RegistrationState
    data object Error : RegistrationState
    data object Empty : RegistrationState
    data class Success(val model: LoginUserModel) : RegistrationState
}