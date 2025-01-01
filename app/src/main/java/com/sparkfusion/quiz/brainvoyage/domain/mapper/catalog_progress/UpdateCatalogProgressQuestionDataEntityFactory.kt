package com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.UpdateCatalogProgressQuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.UpdateCatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class UpdateCatalogProgressQuestionDataEntityFactory @Inject constructor(
) : MapFactory<UpdateCatalogProgressQuestionDataEntity, UpdateCatalogProgressQuestionModel> {

    override suspend fun mapTo(input: UpdateCatalogProgressQuestionDataEntity): UpdateCatalogProgressQuestionModel =
        with(input) {
            UpdateCatalogProgressQuestionModel(quizId, questionId, xpGained, correctAnswer)
        }

    override suspend fun mapFrom(input: UpdateCatalogProgressQuestionModel): UpdateCatalogProgressQuestionDataEntity =
        with(input) {
            UpdateCatalogProgressQuestionDataEntity(quizId, questionId, xpGained, correctAnswer)
        }
}