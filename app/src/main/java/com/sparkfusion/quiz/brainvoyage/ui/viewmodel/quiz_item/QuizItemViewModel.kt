package com.sparkfusion.quiz.brainvoyage.ui.viewmodel.quiz_item

import androidx.lifecycle.viewModelScope
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ICatalogProgressRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRatingRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.common.viewmodel.MultiStateViewModel
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class QuizItemViewModel @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRepository: IQuizRepository,
    private val quizRatingRepository: IQuizRatingRepository,
    private val catalogProgressRepository: ICatalogProgressRepository
) : MultiStateViewModel<QuizItemContract.Intent>() {

    private val _quizLoadingState =
        MutableStateFlow<QuizItemContract.QuizReadingState>(QuizItemContract.QuizReadingState.Loading)
    val quizLoadingState: StateFlow<QuizItemContract.QuizReadingState> =
        _quizLoadingState.asStateFlow()

    override fun handleIntent(intent: QuizItemContract.Intent) {
        when (intent) {
            is QuizItemContract.Intent.ReadQuiz -> readQuiz(intent.quizId)
        }
    }

    private fun readQuiz(quizId: Long) {
        _quizLoadingState.update { QuizItemContract.QuizReadingState.Loading }
        viewModelScope.launch(ioDispatcher) {
            quizRepository.readQuizById(quizId)
                .onSuccess { model ->
                    val progressState = async { loadProgressState(model) }
                    val quizRating = async { loadQuizRating(quizId) }

                    val progressModel = progressState.await()
                        .onFailure {
                            _quizLoadingState.update { QuizItemContract.QuizReadingState.Error }
                        }

                    val quizRatingModel = quizRating.await()
                        .onFailure {
                            _quizLoadingState.update { QuizItemContract.QuizReadingState.Error }
                        }

                    _quizLoadingState.update {
                        QuizItemContract.QuizReadingState.Success(
                            model,
                            calculateRemainingTime(progressModel.unwrap().nextTryAt),
                            quizRatingModel.unwrap()
                        )
                    }
                }
                .onFailure {
                    _quizLoadingState.update { QuizItemContract.QuizReadingState.Error }
                }
        }
    }

    private suspend fun loadQuizRating(quizId: Long): Answer<QuizRatingModel?> {
        return quizRatingRepository.readQuizRating(quizId)
    }

    private suspend fun loadProgressState(quiz: GetQuizPreviewModel): Answer<CatalogProgressModel> {
        return catalogProgressRepository.readCatalogProgress(quiz.id)
//            .onSuccess { model ->
//                _quizLoadingState.update {
//                    QuizItemContract.QuizReadingState.Success(
//                        quiz,
//                        calculateRemainingTime(model.nextTryAt)
//                    )
//                }
//            }
//            .onFailure {
//                _quizLoadingState.update { QuizItemContract.QuizReadingState.Error }
//            }
    }

    private fun calculateRemainingTime(targetTime: LocalDateTime): Duration? {
        val currentTime = LocalDateTime.now()

        return if (currentTime.isAfter(targetTime)) {
            null
        } else {
            Duration.between(currentTime, targetTime)
        }
    }
}























