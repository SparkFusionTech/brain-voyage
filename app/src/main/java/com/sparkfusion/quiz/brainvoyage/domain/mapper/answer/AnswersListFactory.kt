package com.sparkfusion.quiz.brainvoyage.domain.mapper.answer

import com.sparkfusion.quiz.brainvoyage.data.entity.answer.AnswerDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.answer.AnswerModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AnswersListFactory @Inject constructor(
    private val answerDataEntityFactory: AnswerDataEntityFactory
) : MapFactory<List<AnswerDataEntity>, List<AnswerModel>> {

    override suspend fun mapTo(input: List<AnswerDataEntity>): List<AnswerModel> {
        return input.map {
            answerDataEntityFactory.mapTo(it)
        }
    }

    override suspend fun mapFrom(input: List<AnswerModel>): List<AnswerDataEntity> {
        return input.map {
            answerDataEntityFactory.mapFrom(it)
        }
    }
}