package com.sparkfusion.quiz.brainvoyage.domain.mapper.question

import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuestionsListFactory @Inject constructor(
    private val questionDataEntityFactory: QuestionDataEntityFactory
) : MapFactory<List<QuestionDataEntity>, List<QuestionModel>> {

    override suspend fun mapTo(input: List<QuestionDataEntity>): List<QuestionModel> {
        return input.map {
            questionDataEntityFactory.mapTo(it)
        }
    }

    override suspend fun mapFrom(input: List<QuestionModel>): List<QuestionDataEntity> {
        return input.map {
            questionDataEntityFactory.mapFrom(it)
        }
    }
}