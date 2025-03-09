package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.settings

import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState

interface SettingsContract {

    sealed interface SettingsInterface : Intent {
        data object ClearLoginData : SettingsInterface
        data object ReadAccountSignInState : SettingsInterface
        data class DeleteAccount(val pass: String) : SettingsInterface
        data class ChangeAccountSignInState(val value: Boolean) : SettingsInterface
        data class ChangePassword(val pass: String) : SettingsInterface
        data class ChangeNewPasswordDialogVisibility(val value: Boolean) : SettingsInterface
        data class ChangeDeleteAccountDialogVisibility(val value: Boolean) : SettingsInterface
    }

    sealed interface ClearLoginState : UIState {
        data object Initial : ClearLoginState
        data object Error : ClearLoginState
        data object Success : ClearLoginState
        data object Progress : ClearLoginState
    }

    sealed interface ReadSaveSignInState : UIState {
        data object Initial : ReadSaveSignInState
        data object Error : ReadSaveSignInState
        data class Success(val save: Boolean) : ReadSaveSignInState
    }

    sealed interface UpdateSaveSignInState : UIState {
        data object Initial : UpdateSaveSignInState
        data object Error : UpdateSaveSignInState
    }

    sealed interface ChangePasswordState : UIState {
        data object Initial : ChangePasswordState
        data object EmptyValue : ChangePasswordState
        data object Success : ChangePasswordState
        data object Error : ChangePasswordState
    }

    sealed interface DeleteAccountState : UIState {
        data object Initial : DeleteAccountState
        data object Progress : DeleteAccountState
        data object IncorrectPassword : DeleteAccountState
        data object Success : DeleteAccountState
        data object Error : DeleteAccountState
    }

    data class DialogsState(
        val isNewPasswordDialogVisible: Boolean = false,
        val isDeleteAccountDialogVisible: Boolean = false
    ) : UIState
}





















