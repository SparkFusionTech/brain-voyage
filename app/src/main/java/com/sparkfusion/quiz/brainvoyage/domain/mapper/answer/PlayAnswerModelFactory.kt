package com.sparkfusion.quiz.brainvoyage.domain.mapper.answer

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AnswerDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.PlayAnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class PlayAnswerModelFactory @Inject constructor(
) : MapFactory<AnswerDataEntity, PlayAnswerModel> {

    override suspend fun mapTo(input: AnswerDataEntity): PlayAnswerModel = with(input) {
        PlayAnswerModel(id, name, number, correct)
    }

    override suspend fun mapFrom(input: PlayAnswerModel): AnswerDataEntity = with(input) {
        AnswerDataEntity(id, name, number, correct)
    }
}