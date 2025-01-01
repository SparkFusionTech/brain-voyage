package com.sparkfusion.quiz.brainvoyage.ui.drawer

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.mapper.user.AccountInfoDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.AccountInfoModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAccountInfoStore
import com.sparkfusion.quiz.brainvoyage.domain.repository.ICatalogProgressRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
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
    private val loginRepository: ILoginRepository,
    private val catalogProgressRepository: ICatalogProgressRepository
) : MultiStateViewModel<DrawerContract.DrawerIntent>() {

    private val _initialState = MutableStateFlow(DrawerContract.DrawerState())
    val initialState: StateFlow<DrawerContract.DrawerState> = _initialState.asStateFlow()

    private val _levelState = MutableStateFlow<DrawerContract.LevelState>(DrawerContract.LevelState.Initial)
    val levelState: StateFlow<DrawerContract.LevelState> = _levelState.asStateFlow()

    override fun handleIntent(intent: DrawerContract.DrawerIntent) {
        when (intent) {
            DrawerContract.DrawerIntent.ReloadUserInfo -> loadAccountInfo()
            DrawerContract.DrawerIntent.ReloadCatalogLevel -> loadCatalogExperience()
        }
    }

    private fun loadAccountInfo() {
        _initialState.update { it.copy(accountInfoState = DrawerReadingState.Loading) }
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

    private fun loadCatalogExperience() {
        viewModelScope.launch(ioDispatcher) {
            catalogProgressRepository.readCatalogExperience()
                .onSuccess { model ->
                    _levelState.update { DrawerContract.LevelState.Success(model) }
                }
                .onFailure {
                    _levelState.update { DrawerContract.LevelState.Error }
                }
        }
    }

    private suspend fun loadInfoFromNetwork() {
        loginRepository.loadUserInfo()
            .onSuccess(::handleSuccessAccountInfoState)
            .onFailure(::handleFailureAccountInfoState)
    }

    private fun handleFailureAccountInfoState(exception: BrainVoyageException) {
        _initialState.update {
            it.copy(
                accountInfoState = when (exception) {
                    is NetworkException -> DrawerReadingState.NetworkError
                    else -> DrawerReadingState.Error
                }
            )
        }
    }

    private fun handleSuccessAccountInfoState(model: AccountInfoModel) {
        _initialState.update { it.copy(accountInfoState = DrawerReadingState.Success(model)) }
    }

    init {
        loadAccountInfo()
    }
}
