package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared

import androidx.compose.runtime.mutableStateListOf
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedQuestionsViewModel @Inject constructor(
) : MultiStateViewModel<SharedQuestionContract.Intent>() {

    private val _questions = MutableStateFlow(mutableStateListOf<SendQuestionModel>())
    val questions: StateFlow<List<SendQuestionModel>> get() = _questions.asStateFlow()

    override fun handleIntent(intent: SharedQuestionContract.Intent) {
        when (intent) {
            is SharedQuestionContract.Intent.AddQuestion -> addQuestion(intent.question)
        }
    }

    private fun addQuestion(question: SendQuestionModel) {
        _questions.value.add(question)
    }
}




































