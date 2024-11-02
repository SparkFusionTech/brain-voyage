package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiListResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuizApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.request_body.RequestBodyParser
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.AddQuizDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.GetQuizIdDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.GetQuizPreviewDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz.GetQuizPreviewListFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz_catalog.QuizCatalogListFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.AddQuizModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizIdModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepository @Inject constructor(
    private val quizApiService: QuizApiService,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val requestBodyParser: RequestBodyParser,
    private val quizCatalogListFactory: QuizCatalogListFactory,
    private val addQuizDataEntityFactory: AddQuizDataEntityFactory,
    private val getQuizIdDataEntityFactory: GetQuizIdDataEntityFactory,
    private val getQuizPreviewListFactory: GetQuizPreviewListFactory,
    private val getQuizPreviewDataEntityFactory: GetQuizPreviewDataEntityFactory
) : IQuizRepository {

    override suspend fun readQuizzesByCatalogId(catalogId: Long): Answer<List<GetQuizPreviewModel>> =
        safeApiCall(ioDispatcher) {
            ApiListResponseHandler(
                quizApiService.readQuizzesByCatalogId(catalogId),
                ::handleExceptionCode
            )
                .handleFetchedData()
                .suspendMap(getQuizPreviewListFactory::mapTo)
        }

    override suspend fun readCatalog(): Answer<List<QuizCatalogModel>> = safeApiCall(ioDispatcher) {
        ApiListResponseHandler(quizApiService.readCatalog(), ::handleExceptionCode)
            .handleFetchedData()
            .suspendMap(quizCatalogListFactory::mapTo)
    }

    override suspend fun createQuiz(
        addQuizModel: AddQuizModel,
        image: MultipartBody.Part
    ): Answer<GetQuizIdModel> = safeApiCall(ioDispatcher) {
        val requestBody = requestBodyParser.parse(addQuizDataEntityFactory.mapFrom(addQuizModel))
        ApiResponseHandler(
            quizApiService.createQuiz(requestBody, image),
            ::handleExceptionCode
        )
            .handleFetchedData()
            .suspendMap(getQuizIdDataEntityFactory::mapTo)
    }

    override suspend fun readQuizById(quizId: Long): Answer<GetQuizPreviewModel> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(quizApiService.readQuizById(quizId), ::handleExceptionCode)
                .handleFetchedData()
                .suspendMap(getQuizPreviewDataEntityFactory::mapTo)

        }
}
































