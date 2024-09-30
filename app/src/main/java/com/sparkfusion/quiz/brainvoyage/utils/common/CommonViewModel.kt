package com.sparkfusion.quiz.brainvoyage.utils.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class CommonViewModel<State: UIState, ViewIntent: Intent> : ViewModel() {

    abstract fun initialState(): StateFlow<State>
    abstract fun handleIntent(intent: ViewIntent)
}