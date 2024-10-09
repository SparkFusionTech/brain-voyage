package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz_catalog

import com.sparkfusion.quiz.brainvoyage.data.entity.QuizCatalogDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuizCatalogListFactory @Inject constructor(
    private val quizCatalogDataEntityFactory: QuizCatalogDataEntityFactory
) : MapFactory<List<QuizCatalogDataEntity>, List<QuizCatalogModel>> {

    override suspend fun mapTo(input: List<QuizCatalogDataEntity>): List<QuizCatalogModel> {
        return input.map {
            quizCatalogDataEntityFactory.mapTo(it)
        }
    }

    override suspend fun mapFrom(input: List<QuizCatalogModel>): List<QuizCatalogDataEntity> {
        return input.map {
            quizCatalogDataEntityFactory.mapFrom(it)
        }
    }
}
