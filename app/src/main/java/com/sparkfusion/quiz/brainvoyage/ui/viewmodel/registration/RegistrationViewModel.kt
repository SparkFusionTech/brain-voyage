package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.StringToMultipartWorker
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.image.BitmapToFileWorker
import com.sparkfusion.quiz.brainvoyage.utils.image.FailedBitmapToFileConversionException
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageChildren
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageFileToMultipartWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val stringToMultipartWorker: StringToMultipartWorker,
    private val bitmapToFileWorker: BitmapToFileWorker,
    private val imageFileToMultipartWorker: ImageFileToMultipartWorker
) : CommonViewModel<RegistrationContract.RegistrationUIState, RegistrationContract.RegistrationIntent>() {

    override fun initialState(): RegistrationContract.RegistrationUIState = uiState

    override fun handleIntent(intent: RegistrationContract.RegistrationIntent) {
        when (intent) {
            RegistrationContract.RegistrationIntent.Register -> register()
            RegistrationContract.RegistrationIntent.ChangePasswordVisibility -> changePasswordVisibility()
            is RegistrationContract.RegistrationIntent.ChangeEmail -> changeEmail(intent.value)
            is RegistrationContract.RegistrationIntent.ChangePassword -> changePassword(intent.value)
            is RegistrationContract.RegistrationIntent.ChangeAccountIcon -> changeAccountIcon(intent.value)
        }
    }

    private var uiState by mutableStateOf(RegistrationContract.RegistrationUIState())

    private fun register() {
        uiState = uiState.copy(registrationState = RegistrationState.Loading)
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.registerAccount(
                    stringToMultipartWorker(uiState.email),
                    stringToMultipartWorker(uiState.password),
                    getImageMultipart(uiState.accountIcon)
                ).onSuccess {
                    uiState = uiState.copy(registrationState = RegistrationState.Success(it))
                }.onFailure {
                    Log.i("TAGTAG", "ERROR - ${it.message}")
                }
            } catch (exception: FailedBitmapToFileConversionException) {
                Log.i("TAGTAG", "ERROR - $exception")
            }
        }
    }

    private suspend fun getImageMultipart(bitmap: Bitmap?): MultipartBody.Part? {
        if (bitmap == null) return null
        val file = bitmapToFileWorker(bitmap, ImageChildren.PROFILE_ICON)
        return imageFileToMultipartWorker(file, "accountIcon")
    }

    private fun changeAccountIcon(newValue: Bitmap?) {
        if (newValue == uiState.accountIcon) return
        uiState = uiState.copy(accountIcon = newValue)
    }

    private fun changeEmail(newValue: String) {
        if (newValue == uiState.email) return
        uiState = uiState.copy(email = newValue)
    }

    private fun changePassword(newValue: String) {
        if (newValue == uiState.password) return
        uiState = uiState.copy(password = newValue)
    }

    private fun changePasswordVisibility() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }
}
























