package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.registration

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.LoginUserModel
import com.sparkfusion.quiz.brainvoyage.domain.model.UserExistsModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.StringToMultipartWorker
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.SingleStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.AlreadyExistsException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import com.sparkfusion.quiz.brainvoyage.utils.image.ApiImageSerializeNames
import com.sparkfusion.quiz.brainvoyage.utils.image.BitmapSizeReducer
import com.sparkfusion.quiz.brainvoyage.utils.image.BitmapToFileWorker
import com.sparkfusion.quiz.brainvoyage.utils.image.FailedBitmapToFileConversionException
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageChildren
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageFileToMultipartWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val stringToMultipartWorker: StringToMultipartWorker,
    private val bitmapToFileWorker: BitmapToFileWorker,
    private val imageFileToMultipartWorker: ImageFileToMultipartWorker,
    private val bitmapSizeReducer: BitmapSizeReducer
) : SingleStateViewModel<RegistrationContract.RegistrationUIState, RegistrationContract.RegistrationIntent>() {

    override fun initialState(): StateFlow<RegistrationContract.RegistrationUIState> =
        uiState.asStateFlow()

    override fun handleIntent(intent: RegistrationContract.RegistrationIntent) {
        when (intent) {
            RegistrationContract.RegistrationIntent.Register -> register()
            RegistrationContract.RegistrationIntent.ChangePasswordVisibility -> changePasswordVisibility()
            RegistrationContract.RegistrationIntent.ClearRegistrationState -> clearRegistrationState()
            RegistrationContract.RegistrationIntent.CheckUserExistence -> checkUserExistence()
            is RegistrationContract.RegistrationIntent.ChangeEmail -> changeEmail(intent.value)
            is RegistrationContract.RegistrationIntent.ChangePassword -> changePassword(intent.value)
            is RegistrationContract.RegistrationIntent.ChangeAccountIcon -> changeAccountIcon(intent.value)
        }
    }

    private val uiState = MutableStateFlow(RegistrationContract.RegistrationUIState())

    private fun checkUserExistence() {
        uiState.update { it.copy(userExistsState = UserExistsState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            repository.exists(uiState.value.email)
                .onSuccess(::onSuccessUserExistenceResponse)
                .onFailure { onFailureUserExistenceResponse() }
        }
    }

    private fun register() {
        if (uiState.value.registrationState == RegistrationState.Loading) return
        uiState.update { it.copy(registrationState = RegistrationState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.registerAccount(
                    stringToMultipartWorker(uiState.value.email),
                    stringToMultipartWorker(uiState.value.password),
                    getImageMultipart(uiState.value.accountIcon)
                )
                    .onSuccess(::onSuccessRegistrationResponse)
                    .onFailure(::onFailureRegistrationResponse)
            } catch (exception: FailedBitmapToFileConversionException) {
                uiState.update { it.copy(registrationState = RegistrationState.FailedImageHandling) }
            }
        }
    }

    private suspend fun getImageMultipart(bitmap: Bitmap?): MultipartBody.Part? {
        if (bitmap == null) return null
        val file = bitmapToFileWorker(
//            bitmapSizeReducer.reduce(
            bitmap
//            , maxScaleFactor = 1.5f)
            , ImageChildren.PROFILE_ICON
        )
        return imageFileToMultipartWorker(file, ApiImageSerializeNames.ACCOUNT_ICON.value)
    }

    private fun onSuccessUserExistenceResponse(model: UserExistsModel) {
        uiState.update {
            it.copy(userExistsState = if (model.exists) UserExistsState.Exists else UserExistsState.NotExists)
        }
    }

    private fun onFailureUserExistenceResponse() {
        uiState.update { it.copy(userExistsState = UserExistsState.Error) }
    }

    private fun onSuccessRegistrationResponse(model: LoginUserModel) {
        uiState.update {
            it.copy(registrationState = RegistrationState.Success(model))
        }
    }

    private fun onFailureRegistrationResponse(exception: BrainVoyageException) {
        uiState.update {
            it.copy(
                registrationState = when (exception) {
                    is AlreadyExistsException -> RegistrationState.UserAlreadyExists
                    is NetworkException -> RegistrationState.NetworkError
                    else -> RegistrationState.Error
                }
            )
        }
    }

    private fun clearRegistrationState() {
        uiState.update { it.copy(registrationState = RegistrationState.Empty) }
    }

    private fun changeAccountIcon(newValue: Bitmap?) {
        if (newValue == uiState.value.accountIcon) return
        uiState.update { it.copy(accountIcon = newValue) }
    }

    private fun changeEmail(newValue: String) {
        if (newValue == uiState.value.email) return
        uiState.update { it.copy(email = newValue) }
    }

    private fun changePassword(newValue: String) {
        if (newValue == uiState.value.password) return
        uiState.update { it.copy(password = newValue) }
    }

    private fun changePasswordVisibility() {
        uiState.update { it.copy(showPassword = !uiState.value.showPassword) }
    }
}

