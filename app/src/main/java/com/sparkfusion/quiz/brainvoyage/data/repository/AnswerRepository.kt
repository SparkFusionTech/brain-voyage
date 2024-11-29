package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiListResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.AnswerApiService
import com.sparkfusion.quiz.brainvoyage.domain.mapper.answer.AnswersListFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AnswerModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IAnswerRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnswerRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val answerApiService: AnswerApiService,
    private val answersListFactory: AnswersListFactory
) : IAnswerRepository {

    override suspend fun readAnswersByQuestionsId(questionId: Long): Answer<List<AnswerModel>> =
        safeApiCall(ioDispatcher) {
            ApiListResponseHandler(answerApiService.readAnswerByQuestionId(questionId))
                .handleFetchedData()
                .suspendMap(answersListFactory::mapTo)
        }
}






















