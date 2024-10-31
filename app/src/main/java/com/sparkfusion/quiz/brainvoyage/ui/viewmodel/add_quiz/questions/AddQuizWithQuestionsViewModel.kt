package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.questions

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddAnswerModel
import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.AddQuizModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ITagRepository
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.model.question.SendQuestionModel
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.AddQuizInitialModel
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.image.ApiImageSerializeNames
import com.sparkfusion.quiz.brainvoyage.utils.image.BitmapToFileWorker
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageChildren
import com.sparkfusion.quiz.brainvoyage.utils.image.ImageFileToMultipartWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddQuizWithQuestionsViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository,
    private val tagRepository: ITagRepository,
    private val questionRepository: IQuestionRepository,
    private val bitmapToFileWorker: BitmapToFileWorker,
    private val imageFileToMultipartWorker: ImageFileToMultipartWorker
) : MultiStateViewModel<AddQuizWithQuestionsContract.Intent>() {

    private val _state = MutableStateFlow(AddQuizWithQuestionsContract.State())
    val state: StateFlow<AddQuizWithQuestionsContract.State> get() = _state.asStateFlow()

    private val _publishState =
        MutableStateFlow<AddQuizWithQuestionsContract.PublishQuizState>(AddQuizWithQuestionsContract.PublishQuizState.Empty)
    val publishState: StateFlow<AddQuizWithQuestionsContract.PublishQuizState> get() = _publishState.asStateFlow()

    private val _quizAddingState =
        MutableStateFlow<AddQuizWithQuestionsContract.QuizSavingState>(AddQuizWithQuestionsContract.QuizSavingState.Empty)
    val quizAddingState: StateFlow<AddQuizWithQuestionsContract.QuizSavingState> get() = _quizAddingState.asStateFlow()

    override fun handleIntent(intent: AddQuizWithQuestionsContract.Intent) {
        when (intent) {
            is AddQuizWithQuestionsContract.Intent.ChangeCloseDialogVisibility -> changeCloseDialogVisibility(
                intent.value
            )

            is AddQuizWithQuestionsContract.Intent.SaveQuiz -> saveQuiz(
                intent.addQuizInitialModel,
                intent.questions
            )

            AddQuizWithQuestionsContract.Intent.ClearPublishState -> clearPublishState()
        }
    }

    private fun clearPublishState() {
        _publishState.update { AddQuizWithQuestionsContract.PublishQuizState.Empty }
    }

    private fun saveQuiz(
        addQuizInitialModel: AddQuizInitialModel?,
        questions: List<SendQuestionModel>
    ) {
        Log.d("QuizSaving", "Started saving quiz.")

        if (addQuizInitialModel == null) {
            Log.e("QuizSaving", "Quiz model is null.")
            _publishState.update { AddQuizWithQuestionsContract.PublishQuizState.QuizIsNull }
            return
        }

        Log.d("QuizSaving", "Quiz title: ${addQuizInitialModel.title}, description: ${addQuizInitialModel.description}")

        if (questions.size < 5) {
            Log.e("QuizSaving", "Not enough questions: ${questions.size}. Minimum is 5.")
            _publishState.update { AddQuizWithQuestionsContract.PublishQuizState.NotEnoughQuestions }
            return
        }

        _publishState.update { AddQuizWithQuestionsContract.PublishQuizState.Success }

        viewModelScope.launch(ioDispatcher) {
            Log.d("QuizSaving", "Starting quiz saving process.")
            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.QuizSaving }

            val quizSaveAnswer = quizRepository.createQuiz(
                AddQuizModel(
                    addQuizInitialModel.title,
                    addQuizInitialModel.description,
                    questions.size,
                    addQuizInitialModel.catalogId
                ),
                imageFileToMultipartWorker.invoke(
                    bitmapToFileWorker.invoke(addQuizInitialModel.bitmap, ImageChildren.QUIZ_ICON),
                    ApiImageSerializeNames.QUIZ_ICON.value
                )
            ).onFailure {
                Log.e("QuizSaving", "Error occurred while saving quiz: ${it.message}")
                _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.Error }
                return@launch
            }

            Log.d("QuizSaving", "Quiz saved successfully with ID: ${quizSaveAnswer.unwrap().id}")

            tagRepository.createTags(addQuizInitialModel.tags, quizSaveAnswer.unwrap().id)
                .onFailure {
                    Log.e("QuizSaving", "Error occurred while creating tags: ${it.message}")
                    _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.Error }
                    return@launch
                }

            Log.d("QuizSaving", "Tags created successfully for quiz ID: ${quizSaveAnswer.unwrap().id}")

            questions.forEach {
                Log.d("QuizSaving", "Creating question: ${it.name}")

                questionRepository.createQuestion(
                    AddQuestionModel(
                        it.name,
                        it.categoryType.mapToInt(),
                        it.difficulty.mapToInt(),
                        it.explanation,
                        quizSaveAnswer.unwrap().id,
                        it.answers.mapIndexed { index, answerModel ->
                            AddAnswerModel(
                                answerModel.name,
                                index + 1,
                                answerModel.isCorrect
                            )
                        }
                    ),
                    imageFileToMultipartWorker.invoke(
                        bitmapToFileWorker.invoke(it.icon, ImageChildren.QUESTION_ICON),
                        ApiImageSerializeNames.QUESTION_ICON.value
                    )
                )
            }

            _quizAddingState.update { AddQuizWithQuestionsContract.QuizSavingState.Success }
            Log.d("QuizSaving", "Successfully saved quiz and all its questions.")
        }
    }

    private fun changeCloseDialogVisibility(value: Boolean) {
        _state.update { it.copy(showCloseDialog = value) }
    }
}




















