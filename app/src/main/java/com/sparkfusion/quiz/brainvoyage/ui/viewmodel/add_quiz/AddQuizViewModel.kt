package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz

import android.graphics.Bitmap
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
class AddQuizViewModel @Inject constructor() : MultiStateViewModel<AddQuizContract.AddQuizIntent>() {

    private val _state = MutableStateFlow(AddQuizContract.AddQuizState())
    val state: StateFlow<AddQuizContract.AddQuizState> get() = _state.asStateFlow()

    override fun handleIntent(intent: AddQuizContract.AddQuizIntent) {
        when (intent) {
            is AddQuizContract.AddQuizIntent.ChangeTitle -> changeTitle(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeDescription -> changeDescription(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeImageSelectionDialogVisibility -> changeDialogVisibility(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeIcon -> changeIcon(intent.bitmap)
        }
    }

    private fun changeIcon(bitmap: Bitmap?) {
        _state.update { it.copy(bitmap = bitmap) }
    }

    private fun changeDialogVisibility(value: Boolean) {
        _state.update { it.copy(showImageSelectionDialog = value) }
    }

    private fun changeDescription(value: String) {
        _state.update { it.copy(description = value) }
    }

    private fun changeTitle(value: String) {
        _state.update { it.copy(title = value) }
    }
}
































