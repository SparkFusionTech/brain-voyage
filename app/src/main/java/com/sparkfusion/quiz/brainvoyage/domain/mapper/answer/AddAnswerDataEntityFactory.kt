package com.sparkfusion.quiz.brainvoyage.domain.mapper.answer

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AddAnswerDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AddAnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AddAnswerDataEntityFactory @Inject constructor() : MapFactory<AddAnswerDataEntity, AddAnswerModel> {

    override suspend fun mapTo(input: AddAnswerDataEntity): AddAnswerModel = with(input) {
        AddAnswerModel(name, number, isCorrect)
    }

    override suspend fun mapFrom(input: AddAnswerModel): AddAnswerDataEntity = with(input) {
        AddAnswerDataEntity(name, number, isCorrect)
    }
}

















