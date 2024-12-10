package com.sparkfusion.quiz.brainvoyage.domain.mapper.answer

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AnswerDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.PlayAnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class PlayAnswerListFactory @Inject constructor(
    private val playAnswerModelFactory: PlayAnswerModelFactory
): MapFactory<List<AnswerDataEntity>, List<PlayAnswerModel>> {

    override suspend fun mapTo(input: List<AnswerDataEntity>): List<PlayAnswerModel> {
        return input.map { playAnswerModelFactory.mapTo(it) }
    }

    override suspend fun mapFrom(input: List<PlayAnswerModel>): List<AnswerDataEntity> {
        return input.map { playAnswerModelFactory.mapFrom(it) }
    }
}