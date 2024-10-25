package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add

import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SharedQuizViewModel @Inject constructor(
) : MultiStateViewModel<SharedQuizContract.Intent>() {

    private val _quizModel = MutableStateFlow<AddQuizInitialModel?>(null)
    val quizModel: StateFlow<AddQuizInitialModel?> get() = _quizModel.asStateFlow()

    override fun handleIntent(intent: SharedQuizContract.Intent) {
        when (intent) {
            is SharedQuizContract.Intent.SetAddQuizInitialModel -> setQuizInitialModel(intent.addQuizInitialModel)
            SharedQuizContract.Intent.ClearQuizModel -> clearModel()
        }
    }

    private fun clearModel() {
        _quizModel.update { null }
    }

    private fun setQuizInitialModel(addQuizInitialModel: AddQuizInitialModel) {
        _quizModel.update { addQuizInitialModel }
    }
}















