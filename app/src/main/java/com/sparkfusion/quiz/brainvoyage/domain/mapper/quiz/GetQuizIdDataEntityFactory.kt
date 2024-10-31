package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.GetQuizIdDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizIdModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class GetQuizIdDataEntityFactory @Inject constructor() : MapFactory<GetQuizIdDataEntity, GetQuizIdModel> {

    override suspend fun mapTo(input: GetQuizIdDataEntity): GetQuizIdModel = with(input) {
        GetQuizIdModel(id)
    }

    override suspend fun mapFrom(input: GetQuizIdModel): GetQuizIdDataEntity = with(input) {
        GetQuizIdDataEntity(id)
    }
}
