package com.sparkfusion.quiz.brainvoyage.data.mapper.quiz_catalog

import com.sparkfusion.quiz.brainvoyage.data.entity.QuizCatalogDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuizCatalogDataEntityFactory @Inject constructor() :
    MapFactory<QuizCatalogDataEntity, QuizCatalogModel> {

    override suspend fun mapTo(input: QuizCatalogDataEntity): QuizCatalogModel = with(input) {
        QuizCatalogModel(id, name, imageUrl, startGradientColor, endGradientColor)
    }

    override suspend fun mapFrom(input: QuizCatalogModel): QuizCatalogDataEntity = with(input) {
        QuizCatalogDataEntity(id, name, imageUrl, startGradientColor, endGradientColor)
    }
}
