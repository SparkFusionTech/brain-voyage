package com.sparkfusion.quiz.brainvoyage.domain.mapper.answer

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AnswerDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AnswerDataEntityFactory @Inject constructor() : MapFactory<AnswerDataEntity, AnswerModel> {

    override suspend fun mapTo(input: AnswerDataEntity): AnswerModel = with(input) {
        AnswerModel(id, name, number, correct)
    }

    override suspend fun mapFrom(input: AnswerModel): AnswerDataEntity = with(input) {
        AnswerDataEntity(id, name, number, correct)
    }
}