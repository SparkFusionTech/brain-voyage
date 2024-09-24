package com.sparkfusion.quiz.brainvoyage.utils.common

import androidx.lifecycle.ViewModel

abstract class CommonViewModel<State: UIState, ViewIntent: Intent> : ViewModel() {

    abstract fun initialState(): State
    abstract fun handleIntent(intent: ViewIntent)
}