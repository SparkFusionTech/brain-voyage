package com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel

import androidx.lifecycle.ViewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent
import com.sparkfusion.quiz.brainvoyage.utils.common.UIState
import kotlinx.coroutines.flow.StateFlow

abstract class SingleStateViewModel<State: UIState, ViewIntent: Intent> : ViewModel() {

    abstract fun initialState(): StateFlow<State>
    abstract fun handleIntent(intent: ViewIntent)
}
