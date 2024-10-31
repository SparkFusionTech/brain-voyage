package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.handleExceptionCode
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuestionApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.request_body.RequestBodyParser
import com.sparkfusion.quiz.brainvoyage.domain.mapper.question.AddQuestionDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuestionRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val questionApiService: QuestionApiService,
    private val requestBodyParser: RequestBodyParser,
    private val addQuestionDataEntityFactory: AddQuestionDataEntityFactory
) : IQuestionRepository {

    override suspend fun createQuestion(
        addQuestion: AddQuestionModel,
        questionImage: MultipartBody.Part
    ): Answer<Unit> = safeApiCall(ioDispatcher) {
        val requestBody = requestBodyParser.parse(addQuestionDataEntityFactory.mapFrom(addQuestion))
        ApiResponseHandler(
            questionApiService.createQuestion(
                requestBody,
                questionImage
            ),
            ::handleExceptionCode
        ).handleFetchedData()
    }
}



















