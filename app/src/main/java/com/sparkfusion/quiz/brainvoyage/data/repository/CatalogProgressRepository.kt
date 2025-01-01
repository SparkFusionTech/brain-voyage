package com.sparkfusion.quiz.brainvoyage.data.repository

import com.sparkfusion.quiz.brainvoyage.data.common.ApiResponseHandler
import com.sparkfusion.quiz.brainvoyage.data.common.safeApiCall
import com.sparkfusion.quiz.brainvoyage.data.datasource.catalog_progress.CatalogProgressApiService
import com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress.CatalogExperienceDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress.CatalogProgressDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress.CatalogProgressQuestionDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress.UpdateCatalogProgressQuestionDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogExperienceModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.UpdateCatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.repository.ICatalogProgressRepository
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CatalogProgressRepository @Inject constructor(
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    private val catalogProgressApiService: CatalogProgressApiService,
    private val catalogExperienceDataEntityFactory: CatalogExperienceDataEntityFactory,
    private val catalogProgressDataEntityFactory: CatalogProgressDataEntityFactory,
    private val catalogProgressQuestionDataEntityFactory: CatalogProgressQuestionDataEntityFactory,
    private val updateCatalogProgressQuestionDataEntityFactory: UpdateCatalogProgressQuestionDataEntityFactory
) : ICatalogProgressRepository {

    override suspend fun readCatalogExperience(): Answer<CatalogExperienceModel> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(catalogProgressApiService.readCatalogExperience())
                .handleFetchedData()
                .suspendMap(catalogExperienceDataEntityFactory::mapTo)
        }

    override suspend fun updateCatalogExperience(addXp: Int): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(catalogProgressApiService.updateCatalogExperience(addXp))
                .handleFetchedData()
        }

    override suspend fun readCatalogProgress(quizId: Long): Answer<CatalogProgressModel> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(catalogProgressApiService.readCatalogProgress(quizId))
                .handleFetchedData()
                .suspendMap(catalogProgressDataEntityFactory::mapTo)
        }

    override suspend fun updateCatalogProgress(quizId: Long): Answer<Unit> =
        safeApiCall(ioDispatcher) {
            ApiResponseHandler(catalogProgressApiService.updateCatalogProgress(quizId))
                .handleFetchedData()
        }

    override suspend fun readCatalogProgressQuestion(
        quizId: Long,
        questionId: Long
    ): Answer<CatalogProgressQuestionModel> = safeApiCall(ioDispatcher) {
        ApiResponseHandler(
            catalogProgressApiService.readCatalogProgressQuestion(
                questionId,
                questionId
            )
        )
            .handleFetchedData()
            .suspendMap(catalogProgressQuestionDataEntityFactory::mapTo)
    }

    override suspend fun updateCatalogProgressQuestion(
        updateCatalogProgressQuestionModel: UpdateCatalogProgressQuestionModel
    ): Answer<Unit> = safeApiCall(ioDispatcher) {
        ApiResponseHandler(
            catalogProgressApiService.updateCatalogProgressQuestion(
                updateCatalogProgressQuestionDataEntityFactory.mapFrom(
                    updateCatalogProgressQuestionModel
                )
            )
        ).handleFetchedData()
    }
}






















