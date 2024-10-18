package com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel

import androidx.lifecycle.ViewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Intent

abstract class MultiStateViewModel<UserIntent: Intent> : ViewModel() {

    abstract fun handleIntent(intent: UserIntent)
}
