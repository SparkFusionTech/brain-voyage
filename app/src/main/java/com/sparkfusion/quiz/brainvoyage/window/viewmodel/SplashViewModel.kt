package com.sparkfusion.quiz.brainvoyage.window.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CommonViewModel<SplashContract.SplashState, SplashContract.SplashIntent>() {

    override fun initialState(): SplashContract.SplashState = uiState

    override fun handleIntent(intent: SplashContract.SplashIntent) {}

    private fun checkAuthentication() {
        viewModelScope.launch(ioDispatcher) {
            try {
                repository.checkTokenValidation()
                    .onSuccess { onSuccessTokenValidation() }
                    .onFailure { onFailureTokenValidation() }
            } catch (exception: FailedDataStoreOperationException) {
                onFailureTokenValidation()
            }
        }
    }

    private var uiState by mutableStateOf<SplashContract.SplashState>(SplashContract.SplashState.Loading)

    private fun onSuccessTokenValidation() {
        uiState = SplashContract.SplashState.Success
    }

    private fun onFailureTokenValidation() {
        uiState = SplashContract.SplashState.Error
    }

    init {
        checkAuthentication()
    }
}
