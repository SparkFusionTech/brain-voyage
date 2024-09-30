package com.sparkfusion.quiz.brainvoyage.window.viewmodel

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.datastore.FailedDataStoreOperationException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: ILoginRepository,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : CommonViewModel<SplashContract.SplashState, SplashContract.SplashIntent>() {

    override fun initialState(): StateFlow<SplashContract.SplashState> = uiState.asStateFlow()

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

    private val uiState = MutableStateFlow<SplashContract.SplashState>(SplashContract.SplashState.Loading)

    private fun onSuccessTokenValidation() {
        uiState.update { SplashContract.SplashState.Success }
    }

    private fun onFailureTokenValidation() {
        uiState.update { SplashContract.SplashState.Error }
    }

    init {
        checkAuthentication()
    }
}
