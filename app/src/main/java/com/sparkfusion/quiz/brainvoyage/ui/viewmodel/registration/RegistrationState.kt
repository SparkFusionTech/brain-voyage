package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel

sealed interface RegistrationState {
    data object Loading : RegistrationState
    data object Error : RegistrationState
    data object NetworkError : RegistrationState
    data object UserAlreadyExists : RegistrationState
    data object FailedImageHandling : RegistrationState
    data object Empty : RegistrationState
    data class Success(val model: LoginUserModel) : RegistrationState
}