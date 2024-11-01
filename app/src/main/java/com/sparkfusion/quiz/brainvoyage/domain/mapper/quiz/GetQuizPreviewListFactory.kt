package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.GetQuizPreviewDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class GetQuizPreviewListFactory @Inject constructor(
    private val getQuizPreviewDataEntityFactory: GetQuizPreviewDataEntityFactory
) : MapFactory<List<GetQuizPreviewDataEntity>, List<GetQuizPreviewModel>> {

    override suspend fun mapTo(input: List<GetQuizPreviewDataEntity>): List<GetQuizPreviewModel> {
        return input.map { getQuizPreviewDataEntityFactory.mapTo(it) }
    }

    override suspend fun mapFrom(input: List<GetQuizPreviewModel>): List<GetQuizPreviewDataEntity> {
        return input.map { getQuizPreviewDataEntityFactory.mapFrom(it) }
    }
}
