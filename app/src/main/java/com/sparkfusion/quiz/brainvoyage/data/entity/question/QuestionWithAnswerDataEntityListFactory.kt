package com.sparkfusion.quiz.brainvoyage.data.entity.question

import com.sparkfusion.quiz.brainvoyage.domain.mapper.question.QuestionWithAnswersDataEntityMapper
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuestionWithAnswerDataEntityListFactory @Inject constructor(
    private val questionWithAnswerDataEntityFactory: QuestionWithAnswersDataEntityMapper
): MapFactory<List<QuestionWithAnswersDataEntity>, List<QuestionWithAnswersModel>> {

    override suspend fun mapTo(input: List<QuestionWithAnswersDataEntity>): List<QuestionWithAnswersModel> {
        return input.map { questionWithAnswerDataEntityFactory.mapTo(it) }
    }

    override suspend fun mapFrom(input: List<QuestionWithAnswersModel>): List<QuestionWithAnswersDataEntity> {
        return input.map { questionWithAnswerDataEntityFactory.mapFrom(it) }
    }
}