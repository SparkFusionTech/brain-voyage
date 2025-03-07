package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.login

import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.sparkfusion.quiz.brainvoyage.data.datasource.workmanager.UserInfoWorker
import com.sparkfusion.quiz.brainvoyage.domain.mapper.user.AccountInfoDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.user.LoginUserDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.domain.model.TokenModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountEmailStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISaveAccountSignInStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.SingleStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val loginUserDataEntityFactory: LoginUserDataEntityFactory,
    private val session: ISession,
    private val saveAccountSignInStore: ISaveAccountSignInStore,
    private val accountEmailStore: IAccountEmailStore,
    private val accountInfoStore: IAccountInfoStore,
    private val accountInfoDataEntityFactory: AccountInfoDataEntityFactory,
    private val workManager: WorkManager
) : SingleStateViewModel<LoginContract.LoginUIState, LoginContract.LoginIntent>() {

    override fun initialState(): StateFlow<LoginContract.LoginUIState> = uiState.asStateFlow()

    override fun handleIntent(intent: LoginContract.LoginIntent) = when (intent) {
        LoginContract.LoginIntent.Login -> login()
        is LoginContract.LoginIntent.HandleRegistrationData -> handleRegistrationData(intent.pair)
        is LoginContract.LoginIntent.ChangeEmail -> changeEmail(intent.email)
        is LoginContract.LoginIntent.ChangePassword -> changePassword(intent.password)
    }

    private val uiState = MutableStateFlow(LoginContract.LoginUIState())

    private fun login() {
        uiState.update { it.copy(loginState = LoginState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            val user = LoginUserModel(uiState.value.email, uiState.value.password)
            loginRepository.authenticate(loginUserDataEntityFactory.mapFrom(user))
                .onSuccess(::onSuccessLoginState)
                .onFailure(::onFailureLoginState)
        }
    }

    private fun startWorkManagerToSaveAccountInfo() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<UserInfoWorker>()
            .setConstraints(constraints)
            .addTag(UserInfoWorker.TAG)
            .build()

        workManager.enqueue(request)
    }

    private fun onSuccessLoginState(tokenModel: TokenModel) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val save = saveAccountSignInStore.readSaveAccountSignIn().firstOrNull() ?: true
                if (save) {
                    accountEmailStore.changeAccountEmail(uiState.value.email)
                    session.saveUserToken(tokenModel.token)
                    saveAccountInfo()
//                    startWorkManagerToSaveAccountInfo()
                }
            } catch (ignore: FailedDataStoreOperationException) {
            } finally {
                uiState.update { it.copy(loginState = LoginState.Success) }
            }
        }
    }

    private fun saveAccountInfo() {
        viewModelScope.launch {
            loginRepository.loadUserInfo()
                .onSuccess { entity ->
                    accountInfoStore.saveAccountInfo(accountInfoDataEntityFactory.mapFrom(entity))
                }
        }
    }

    private fun onFailureLoginState(exception: BrainVoyageException) {
        uiState.update {
            it.copy(
                loginState = when (exception) {
                    is NetworkException -> LoginState.NetworkError
                    else -> LoginState.Error
                }
            )
        }
    }

    private fun handleRegistrationData(pair: Pair<String?, String?>) {
        val (email, password) = pair
        if (email == null || password == null) return
        uiState.update { it.copy(email = email, password = password) }
    }

    private fun changeEmail(email: String) {
        uiState.update { it.copy(email = email) }
    }

    private fun changePassword(password: String) {
        uiState.update { it.copy(password = password) }
    }
}
