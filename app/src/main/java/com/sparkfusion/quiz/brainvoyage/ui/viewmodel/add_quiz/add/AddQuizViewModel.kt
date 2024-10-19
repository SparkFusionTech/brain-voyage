package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddQuizViewModel @Inject constructor() : MultiStateViewModel<AddQuizContract.AddQuizIntent>() {

    private val _state = MutableStateFlow(AddQuizContract.AddQuizState())
    val state: StateFlow<AddQuizContract.AddQuizState> get() = _state.asStateFlow()

    private val _tagsState = MutableStateFlow(mutableStateListOf<String>())
    val tagsState: StateFlow<List<String>> get() = _tagsState.asStateFlow()

    private val _sendModelState = MutableStateFlow<SendQuizAnswer>(SendQuizAnswer.Empty)
    val sendModelState: StateFlow<SendQuizAnswer> get() = _sendModelState.asStateFlow()

    override fun handleIntent(intent: AddQuizContract.AddQuizIntent) {
        when (intent) {
            is AddQuizContract.AddQuizIntent.ChangeTitle -> changeTitle(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeDescription -> changeDescription(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeImageSelectionDialogVisibility -> changeImageSelectionDialogVisibility(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeTagAddingDialogVisibility -> changeTagAddingDialogVisibility(intent.value)
            is AddQuizContract.AddQuizIntent.ChangeIcon -> changeIcon(intent.bitmap)
            is AddQuizContract.AddQuizIntent.AddTag -> addTag(intent.tag)
            is AddQuizContract.AddQuizIntent.DeleteTag -> deleteTag(intent.id)
            AddQuizContract.AddQuizIntent.LoadSendQuizState -> loadSendQuizState()
            AddQuizContract.AddQuizIntent.ClearSendQuizState -> clearQuizSendState()
        }
    }

    private fun clearQuizSendState() {
        _sendModelState.update { SendQuizAnswer.Empty }
    }

    private fun loadSendQuizState() {
        if (state.value.bitmap == null) {
            _sendModelState.update { SendQuizAnswer.ImageIsNotSelected }
            return
        }

        if (state.value.title.length < 3) {
            _sendModelState.update { SendQuizAnswer.TitleIsTooShort }
            return
        }

        if (state.value.description.length < 12) {
            _sendModelState.update { SendQuizAnswer.DescriptionIsTooShort }
            return
        }

        _sendModelState.update {
            SendQuizAnswer.Success(
                AddQuizInitialModel(
                    bitmap = state.value.bitmap!!,
                    title = state.value.title,
                    description = state.value.description,
                    tags = tagsState.value
                )
            )
        }
    }

    private fun deleteTag(id: Int) {
        _tagsState.update {
            it.apply { removeAt(id) }
        }
    }

    private fun addTag(tag: String) {
        _tagsState.update {
            it.apply { add(tag) }
        }
    }

    private fun changeTagAddingDialogVisibility(value: Boolean) {
        _state.update { it.copy(showTagAddingDialog = value) }
    }

    private fun changeIcon(bitmap: Bitmap?) {
        _state.update { it.copy(bitmap = bitmap) }
    }

    private fun changeImageSelectionDialogVisibility(value: Boolean) {
        _state.update { it.copy(showImageSelectionDialog = value) }
    }

    private fun changeDescription(value: String) {
        _state.update { it.copy(description = value) }
    }

    private fun changeTitle(value: String) {
        _state.update { it.copy(title = value) }
    }
}
































