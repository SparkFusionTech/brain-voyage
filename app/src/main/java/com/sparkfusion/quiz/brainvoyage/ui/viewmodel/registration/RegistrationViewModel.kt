package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.ui.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): CommonViewModel<RegistrationContract.RegistrationUIState, RegistrationContract.RegistrationIntent>() {

    override fun initialState(): RegistrationContract.RegistrationUIState = uiState

    override fun handleIntent(intent: RegistrationContract.RegistrationIntent) {
        when (intent) {
            RegistrationContract.RegistrationIntent.Register -> register()
            RegistrationContract.RegistrationIntent.ChangePasswordVisibility -> changePasswordVisibility()
            is RegistrationContract.RegistrationIntent.ChangeEmail -> changeEmail(intent.value)
            is RegistrationContract.RegistrationIntent.ChangePassword -> changePassword(intent.value)
        }
    }

    private var uiState by mutableStateOf(RegistrationContract.RegistrationUIState())

    private fun register() {
        viewModelScope.launch(ioDispatcher) {

        }
    }

    private fun changeEmail(newValue: String) {
        uiState = uiState.copy(email = newValue)
    }

    private fun changePassword(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    private fun changePasswordVisibility() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }
}
























