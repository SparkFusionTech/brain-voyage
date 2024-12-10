package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory

import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VictoryQuizViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : MultiStateViewModel<VictoryQuizContract.VictoryQuizIntent>() {

    private val _initialState = MutableStateFlow<VictoryQuizContract.InitialState?>(null)
    val initialState: StateFlow<VictoryQuizContract.InitialState?> get() = _initialState.asStateFlow()

    override fun handleIntent(intent: VictoryQuizContract.VictoryQuizIntent) {
        when (intent) {
            is VictoryQuizContract.VictoryQuizIntent.InitVictoryScreen -> initState(intent.state)
        }
    }

    private fun initState(state: VictoryQuizContract.InitialState) {
        _initialState.update { state }
    }
}


















