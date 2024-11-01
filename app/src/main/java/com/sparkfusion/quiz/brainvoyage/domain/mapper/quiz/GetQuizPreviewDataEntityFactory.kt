package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.GetQuizPreviewDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class GetQuizPreviewDataEntityFactory @Inject constructor(
) : MapFactory<GetQuizPreviewDataEntity, GetQuizPreviewModel> {

    override suspend fun mapTo(input: GetQuizPreviewDataEntity): GetQuizPreviewModel = with(input) {
        GetQuizPreviewModel(id, title, description, rating, imageUrl, questions)
    }

    override suspend fun mapFrom(input: GetQuizPreviewModel): GetQuizPreviewDataEntity = with(input) {
        GetQuizPreviewDataEntity(id, title, description, rating, imageUrl, questions)
    }
}