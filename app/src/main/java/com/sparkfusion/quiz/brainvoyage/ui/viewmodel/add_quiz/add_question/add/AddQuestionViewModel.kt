package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.add

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer.QuestionAnswerModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.answer.getTrueFalseCategoryAnswers
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.CategoryType
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.category.initCategories
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.difficulty.DifficultyModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.difficulty.initDifficulties
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuestionViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : MultiStateViewModel<AddQuestionContract.Intent>() {

    val categories: Array<CategoryModel> = initCategories()
    val difficulties: Array<DifficultyModel> = initDifficulties()

    private val _commonState = MutableStateFlow(AddQuestionContract.CommonState())
    val commonState: StateFlow<AddQuestionContract.CommonState> get() = _commonState.asStateFlow()

    private val _imageState = MutableStateFlow(AddQuestionContract.ImageState())
    val imageState: StateFlow<AddQuestionContract.ImageState> get() = _imageState.asStateFlow()

    private val _answersState = MutableStateFlow(mutableStateListOf<QuestionAnswerModel>())
    val answersState: StateFlow<List<QuestionAnswerModel>> get() = _answersState.asStateFlow()

    private val _dialogsState = MutableStateFlow(AddQuestionContract.DialogsState())
    val dialogsState: StateFlow<AddQuestionContract.DialogsState> get() = _dialogsState.asStateFlow()

    private val _errorState = MutableStateFlow<AddQuestionContract.ErrorState>(AddQuestionContract.ErrorState.Empty)
    val errorState: StateFlow<AddQuestionContract.ErrorState> get() = _errorState.asStateFlow()

    override fun handleIntent(intent: AddQuestionContract.Intent) {
        when (intent) {
            is AddQuestionContract.Intent.ChangeCategory -> changeCategory(intent.id, intent.trueFalseValues)
            is AddQuestionContract.Intent.ChangeDescription -> changeDescription(intent.value)
            is AddQuestionContract.Intent.ChangeDifficulty -> changeDifficulty(intent.id)
            is AddQuestionContract.Intent.ChangeIcon -> changeIcon(intent.icon)
            is AddQuestionContract.Intent.ChangeQuestion -> changeQuestion(intent.value)
            is AddQuestionContract.Intent.AddAnswer -> addAnswer(intent.answer)
            is AddQuestionContract.Intent.DeleteAnswer -> deleteAnswer(intent.id)
            is AddQuestionContract.Intent.ChangeNewAnswerDialogVisibility -> changeNewAnswerDialogVisibility(intent.value)
            is AddQuestionContract.Intent.ChangeCloseDialogVisibility -> changeCloseDialogVisibility(intent.value)
            is AddQuestionContract.Intent.ChangeImageSelectionDialogVisibility -> changeImageSelectionDialogVisibility(intent.value)
            is AddQuestionContract.Intent.ChangeSelectedRadioButton -> changeSelectedRadioButton(intent.id)
            is AddQuestionContract.Intent.ChangeSelectedCheckButton -> changeSelectedCheckButton(intent.id, intent.value)
            is AddQuestionContract.Intent.ChangeCategoryListVisibility -> changeCategoryListVisibility(intent.value)
            AddQuestionContract.Intent.HandleQuestionAdding -> handleQuestionAdding()
            AddQuestionContract.Intent.ClearQuestionAddingState -> clearQuestionAddingState()
        }
    }

    private fun clearQuestionAddingState() {
        _errorState.update { AddQuestionContract.ErrorState.Empty }
    }

    private fun changeCategoryListVisibility(value: Boolean) {
        _commonState.update { it.copy(isCategoryListVisible = value) }
    }

    private fun changeImageSelectionDialogVisibility(value: Boolean) {
        _dialogsState.update { it.copy(showImageSelectionDialog = value) }
    }

    private fun changeCloseDialogVisibility(value: Boolean) {
        _dialogsState.update { it.copy(showCloseDialog = value) }
    }

    private fun handleQuestionAdding() {
        viewModelScope.launch(defaultDispatcher) {
            val bitmap = imageState.value.bitmap
            if (bitmap == null) {
                _errorState.update { AddQuestionContract.ErrorState.EmptyImage }
                return@launch
            }

            if (commonState.value.question.length < 6) {
                _errorState.update { AddQuestionContract.ErrorState.ShortQuestion }
                return@launch
            }

            val categoryType = categories[commonState.value.currentCategoryId].type
            when (categoryType) {
                CategoryType.MultiplyChoice, CategoryType.Quiz -> {
                    if (_answersState.value.size < 4) {
                        _errorState.update { AddQuestionContract.ErrorState.NotEnoughAnswers }
                        return@launch
                    }
                }

                CategoryType.TrueFalse -> {
                    if (_answersState.value.size < 2) {
                        _errorState.update { AddQuestionContract.ErrorState.NotEnoughAnswers }
                        return@launch
                    }
                }
            }

            var hasCorrect = false
            answersState.value.forEach {
                if (hasCorrect) return@forEach
                hasCorrect = it.isCorrect
            }
            if (!hasCorrect) {
                _errorState.update { AddQuestionContract.ErrorState.NoCorrectAnswer }
                return@launch
            }

            _errorState.update {
                AddQuestionContract.ErrorState.Success(
                    SendQuestionModel(
                        name = commonState.value.question,
                        icon = bitmap,
                        categoryType = categoryType,
                        explanation = commonState.value.description,
                        difficulty = difficulties[commonState.value.currentDifficultyId].difficulty,
                        answers = answersState.value
                    )
                )
            }
        }
    }

    private fun changeSelectedCheckButton(id: Int, value: Boolean) {
        _answersState.value[id] = answersState.value[id].copy(isCorrect = value)
    }

    private fun changeSelectedRadioButton(id: Int) {
        viewModelScope.launch(defaultDispatcher) {
            _answersState.value = _answersState.value.map {
                if (it.isCorrect) it.copy(isCorrect = false) else it
            }.toMutableStateList()
            _answersState.value[id] = answersState.value[id].copy(isCorrect = true)
        }
    }

    private fun changeNewAnswerDialogVisibility(value: Boolean) {
        _dialogsState.update { it.copy(showNewAnswerDialog = value) }
    }

    private fun deleteAnswer(id: Int) {
        if (id < 0 || id > _answersState.value.size - 1) return
        _answersState.value.removeAt(id)
    }

    private fun addAnswer(answer: String) {
        _answersState.value.add(
            QuestionAnswerModel(answer, answersState.value.isEmpty())
        )
    }

    private fun changeDifficulty(id: Int) {
        if (id < 0 || id > difficulties.size - 1) return
        _commonState.update { it.copy(currentDifficultyId = id) }
    }

    private fun changeCategory(id: Int, trueFalseValues: List<String>) {
        viewModelScope.launch(defaultDispatcher) {
            if (id < 0 || id > categories.size - 1) return@launch

            val currentCategoryType = categories[commonState.value.currentCategoryId].type
            val newCategoryType = categories[id].type

            if (currentCategoryType == CategoryType.TrueFalse) _answersState.value.clear()
            if (currentCategoryType == CategoryType.MultiplyChoice) {
                _answersState.value = _answersState.value.map { it.copy(isCorrect = false) }.toMutableStateList()
                if (answersState.value.size > 1) {
                    _answersState.value[0] = answersState.value[0].copy(isCorrect = true)
                }
            }
            if (newCategoryType == CategoryType.TrueFalse) {
                _answersState.value.clear()
                _commonState.update { it.copy(currentDifficultyId = 0) }
                getTrueFalseCategoryAnswers(trueFalseValues).forEach { addAnswer(it.name) }
            }

            _commonState.update { it.copy(currentCategoryId = id) }
        }
    }

    private fun changeIcon(icon: Bitmap?) {
        if (icon == null) return
        _imageState.update { it.copy(bitmap = icon) }
    }

    private fun changeQuestion(value: String) {
        _commonState.update { it.copy(question = value) }
    }

    private fun changeDescription(value: String) {
        _commonState.update { it.copy(description = value) }
    }
}






















