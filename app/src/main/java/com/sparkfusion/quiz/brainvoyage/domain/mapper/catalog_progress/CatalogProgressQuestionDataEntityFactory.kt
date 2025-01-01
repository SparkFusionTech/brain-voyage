package com.sparkfusion.quiz.brainvoyage.domain.mapper.catalog_progress

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogProgressQuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class CatalogProgressQuestionDataEntityFactory @Inject constructor(
) : MapFactory<CatalogProgressQuestionDataEntity, CatalogProgressQuestionModel> {

    override suspend fun mapTo(input: CatalogProgressQuestionDataEntity): CatalogProgressQuestionModel =
        with(input) {
            CatalogProgressQuestionModel(id, questionId, xpGained, correctAnswer)
        }

    override suspend fun mapFrom(input: CatalogProgressQuestionModel): CatalogProgressQuestionDataEntity =
        with(input) {
            CatalogProgressQuestionDataEntity(id, questionId, xpGained, correctAnswer)
        }
}
