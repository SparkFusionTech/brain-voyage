package com.sparkfusion.quiz.brainvoyage.ui.drawer

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.mapper.user.AccountInfoDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.AccountInfoModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.CommonViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val accountInfoStore: IAccountInfoStore,
    private val accountInfoDataEntityFactory: AccountInfoDataEntityFactory,
    private val loginRepository: ILoginRepository
) : CommonViewModel<DrawerContract.DrawerState, DrawerContract.DrawerIntent>() {

    override fun initialState(): StateFlow<DrawerContract.DrawerState> = state.asStateFlow()

    override fun handleIntent(intent: DrawerContract.DrawerIntent) {
        when (intent) {
            DrawerContract.DrawerIntent.ReloadUserInfo -> loadAccountInfo()
        }
    }

    private val state = MutableStateFlow(DrawerContract.DrawerState())

    private fun loadAccountInfo() {
        state.update { it.copy(accountInfoState = DrawerReadingState.Loading) }
        viewModelScope.launch(ioDispatcher) {
            try {
                accountInfoStore.readAccountInfo().collectLatest { entity ->
                    val model = accountInfoDataEntityFactory.mapTo(entity)
                    if (model.name.isNotBlank() && model.iconUrl.isNotBlank()) {
                        handleSuccessAccountInfoState(model)
                    } else {
                        loadInfoFromNetwork()
                    }
                }
            } catch (exception: Exception) {
                loadInfoFromNetwork()
            }
        }
    }

    private suspend fun loadInfoFromNetwork() {
        loginRepository.loadUserInfo()
            .onSuccess(::handleSuccessAccountInfoState)
            .onFailure(::handleFailureAccountInfoState)
    }

    private fun handleFailureAccountInfoState(exception: BrainVoyageException) {
        state.update {
            it.copy(
                accountInfoState = when (exception) {
                    is NetworkException -> DrawerReadingState.NetworkError
                    else -> DrawerReadingState.Error
                }
            )
        }
    }

    private fun handleSuccessAccountInfoState(model: AccountInfoModel) {
        state.update { it.copy(accountInfoState = DrawerReadingState.Success(model)) }
    }

    init {
        loadAccountInfo()
    }
}
