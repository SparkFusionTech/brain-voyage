package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiListResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuizApiService
import com.sparkfusion.quiz.brainvoyage.data.mapper.quiz_catalog.QuizCatalogListFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val quizApiService: QuizApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val quizCatalogListFactory: QuizCatalogListFactory
) : IQuizRepository {

    override suspend fun readCatalog(): Answer<List<QuizCatalogModel>> = safeApiCall(ioDispatcher) {
        ApiListResponseHandler(quizApiService.readCatalog(), ::handleExceptionCode)
            .handleFetchedData()
            .suspendMap(quizCatalogListFactory::mapTo)
    }
}




















