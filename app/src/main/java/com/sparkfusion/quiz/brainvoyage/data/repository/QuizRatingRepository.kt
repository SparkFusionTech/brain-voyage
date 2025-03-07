package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuizRatingApiService
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.rating.QuizRatingDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.rating.UserQuizRatingDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.QuizRatingModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.rating.UserQuizRatingModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRatingRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRatingRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizRatingApiService: QuizRatingApiService,
    private val userQuizRatingDataEntityFactory: UserQuizRatingDataEntityFactory,
    private val quizRatingDataEntityFactory: QuizRatingDataEntityFactory
) : IQuizRatingRepository {

    override suspend fun readQuizRating(quizId: Long): Answer<QuizRatingModel?> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(quizRatingApiService.readQuizRating(quizId))
                .handleFetchedData()
                .suspendMap { entity ->
                    entity?.let {
                        quizRatingDataEntityFactory.mapTo(it)
                    }
                }
        }

    override suspend fun readUserQuizRating(quizId: Long): Answer<UserQuizRatingModel?> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(quizRatingApiService.readUserQuizRating(quizId))
                .handleFetchedData()
                .suspendMap { entity ->
                    entity?.let {
                        userQuizRatingDataEntityFactory.mapTo(it)
                    }
                }
        }

    override suspend fun updateRating(quizId: Long, rating: Int): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(quizRatingApiService.updateRating(quizId, rating))
                .handleFetchedData()
        }


}























