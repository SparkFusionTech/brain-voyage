package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CommonViewModel<LoginContract.LoginUIState, LoginContract.LoginIntent>() {

    override fun initialState(): LoginContract.LoginUIState = uiState

    override fun handleIntent(intent: LoginContract.LoginIntent) = when (intent) {
        LoginContract.LoginIntent.Login -> {  }
        is LoginContract.LoginIntent.ChangeEmail -> changeEmail(intent.email)
        is LoginContract.LoginIntent.ChangePassword -> changePassword(intent.password)
    }

    private var uiState: LoginContract.LoginUIState by mutableStateOf(LoginContract.LoginUIState())

    private fun changeEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    private fun changePassword(password: String) {
        uiState = uiState.copy(password = password)
    }
}





























