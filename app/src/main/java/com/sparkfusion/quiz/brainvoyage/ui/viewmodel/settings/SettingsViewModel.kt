package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.settings

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISaveAccountSignInStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
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
class SettingsViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val session: ISession,
    private val accountInfoStore: IAccountInfoStore,
    private val saveAccountSignInStore: ISaveAccountSignInStore
) : MultiStateViewModel<SettingsContract.SettingsInterface>() {

    private val _clearLoginState =
        MutableStateFlow<SettingsContract.ClearLoginState>(SettingsContract.ClearLoginState.Initial)
    val clearLoginState: StateFlow<SettingsContract.ClearLoginState> =
        _clearLoginState.asStateFlow()

    private val _readSignInState =
        MutableStateFlow<SettingsContract.ReadSaveSignInState>(SettingsContract.ReadSaveSignInState.Initial)
    val readSignInState: StateFlow<SettingsContract.ReadSaveSignInState> =
        _readSignInState.asStateFlow()

    private val _updateSignInState =
        MutableStateFlow<SettingsContract.UpdateSaveSignInState>(SettingsContract.UpdateSaveSignInState.Initial)
    val updateSignInState: StateFlow<SettingsContract.UpdateSaveSignInState> =
        _updateSignInState.asStateFlow()

    private val _dialogsState = MutableStateFlow(SettingsContract.DialogsState())
    val dialogsState: StateFlow<SettingsContract.DialogsState> = _dialogsState.asStateFlow()

    private val _changePasswordState =
        MutableStateFlow<SettingsContract.ChangePasswordState>(SettingsContract.ChangePasswordState.Initial)
    val changePasswordState: StateFlow<SettingsContract.ChangePasswordState> =
        _changePasswordState.asStateFlow()

    private val _deleteAccountState =
        MutableStateFlow<SettingsContract.DeleteAccountState>(SettingsContract.DeleteAccountState.Initial)
    val deleteAccountState: StateFlow<SettingsContract.DeleteAccountState> =
        _deleteAccountState.asStateFlow()

    override fun handleIntent(intent: SettingsContract.SettingsInterface) {
        when (intent) {
            SettingsContract.SettingsInterface.ClearLoginData -> clearLoginSettings()
            SettingsContract.SettingsInterface.ReadAccountSignInState -> readAccountSignInState()
            is SettingsContract.SettingsInterface.ChangeAccountSignInState -> updateAccountSignInState(
                intent.value
            )

            is SettingsContract.SettingsInterface.ChangePassword -> changePassword(intent.pass)
            is SettingsContract.SettingsInterface.DeleteAccount -> deleteAccount(intent.pass)
            is SettingsContract.SettingsInterface.ChangeNewPasswordDialogVisibility -> changeNewPasswordDialogVisibility(
                intent.value
            )

            is SettingsContract.SettingsInterface.ChangeDeleteAccountDialogVisibility -> changeDeleteAccountDialogVisibility(
                intent.value
            )
        }
    }

    private fun changeDeleteAccountDialogVisibility(value: Boolean) {
        _dialogsState.update { it.copy(isDeleteAccountDialogVisible = value) }
    }

    private fun changeNewPasswordDialogVisibility(value: Boolean) {
        _dialogsState.update { it.copy(isNewPasswordDialogVisible = value) }
    }

    private fun deleteAccount(pass: String) {
        viewModelScope.launch(ioDispatcher) {

        }
    }

    private fun updateAccountSignInState(value: Boolean) {
        _updateSignInState.update { SettingsContract.UpdateSaveSignInState.Initial }
        viewModelScope.launch(ioDispatcher) {
            try {
                val v = saveAccountSignInStore.changeSaveAccountSignIn(value).firstOrNull() ?: true
                _readSignInState.update { SettingsContract.ReadSaveSignInState.Success(v) }
            } catch (e: FailedDataStoreOperationException) {
                _updateSignInState.update { SettingsContract.UpdateSaveSignInState.Error }
            }
        }
    }

    private fun readAccountSignInState() {
        viewModelScope.launch(ioDispatcher) {
            try {
                val value = saveAccountSignInStore.readSaveAccountSignIn().firstOrNull() ?: true
                _readSignInState.update { SettingsContract.ReadSaveSignInState.Success(value) }
            } catch (e: FailedDataStoreOperationException) {
                _readSignInState.update { SettingsContract.ReadSaveSignInState.Error }
            }
        }
    }

    private fun changePassword(pass: String) {
        viewModelScope.launch(ioDispatcher) {

        }
    }

    private fun clearLoginSettings() {
        _clearLoginState.update { SettingsContract.ClearLoginState.Initial }
        viewModelScope.launch(ioDispatcher) {
            try {
                accountInfoStore.clearAccountInfo()
                saveAccountSignInStore.clearSaveAccountSingIn()
                session.clearUserToken()
                _clearLoginState.update { SettingsContract.ClearLoginState.Success }
            } catch (e: FailedDataStoreOperationException) {
                _clearLoginState.update { SettingsContract.ClearLoginState.Error }
            }

        }
    }
}




























