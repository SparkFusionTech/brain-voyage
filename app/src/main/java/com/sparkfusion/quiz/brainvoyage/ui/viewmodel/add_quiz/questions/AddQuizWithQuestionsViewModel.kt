package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddQuizWithQuestionsViewModel @Inject constructor(

): MultiStateViewModel<AddQuizWithQuestionsContract.Intent>() {

    private val _state = MutableStateFlow(AddQuizWithQuestionsContract.State())
    val state: StateFlow<AddQuizWithQuestionsContract.State> get() = _state.asStateFlow()

    override fun handleIntent(intent: AddQuizWithQuestionsContract.Intent) {
        when (intent) {
            is AddQuizWithQuestionsContract.Intent.ChangeCloseDialogVisibility -> changeCloseDialogVisibility(intent.value)
        }
    }

    private fun changeCloseDialogVisibility(value: Boolean) {
        _state.update { it.copy(showCloseDialog = value) }
    }

}




















