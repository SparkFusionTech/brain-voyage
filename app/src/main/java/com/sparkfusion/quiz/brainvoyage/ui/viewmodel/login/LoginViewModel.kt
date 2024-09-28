package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.data.mapper.LoginUserDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.domain.model.TokenModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginUserDataEntityFactory: LoginUserDataEntityFactory,
    private val session: ISession
) : CommonViewModel<LoginContract.LoginUIState, LoginContract.LoginIntent>() {

    override fun initialState(): LoginContract.LoginUIState = uiState

    override fun handleIntent(intent: LoginContract.LoginIntent) = when (intent) {
        LoginContract.LoginIntent.Login -> login()
        is LoginContract.LoginIntent.HandleRegistrationData -> handleRegistrationData(intent.pair)
        is LoginContract.LoginIntent.ChangeEmail -> changeEmail(intent.email)
        is LoginContract.LoginIntent.ChangePassword -> changePassword(intent.password)
    }

    private var uiState: LoginContract.LoginUIState by mutableStateOf(LoginContract.LoginUIState())

    private fun login() {
        uiState = uiState.copy(loginState = LoginState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val user = LoginUserModel(uiState.email, uiState.password)
            loginRepository.authenticate(loginUserDataEntityFactory.mapFrom(user))
                .onSuccess(::onSuccessLoginState)
                .onFailure(::onFailureLoginState)
        }
    }

    private fun onSuccessLoginState(tokenModel: TokenModel) {
        viewModelScope.launch(ioDispatcher) {
            try {
                session.saveUserToken(tokenModel.token)
            } catch (ignore: FailedDataStoreOperationException) {
            } finally {
                uiState = uiState.copy(loginState = LoginState.Success)
            }
        }
    }

    private fun onFailureLoginState(exception: BrainVoyageException) {
        uiState = uiState.copy(
            loginState = when (exception) {
                is NetworkException -> LoginState.NetworkError
                else -> LoginState.Error
            }
        )
    }

    private fun handleRegistrationData(pair: Pair<String?, String?>) {
        val (email, password) = pair
        if (email == null || password == null) return
        uiState = uiState.copy(email = email, password = password)
    }

    private fun changeEmail(email: String) {
        uiState = uiState.copy(email = email)
    }

    private fun changePassword(password: String) {
        uiState = uiState.copy(password = password)
    }
}
