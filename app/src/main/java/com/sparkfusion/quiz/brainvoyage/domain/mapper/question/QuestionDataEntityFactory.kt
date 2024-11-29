package com.sparkfusion.quiz.brainvoyage.domain.mapper.question

import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuestionDataEntityFactory @Inject constructor() :
    MapFactory<QuestionDataEntity, QuestionModel> {

    override suspend fun mapTo(input: QuestionDataEntity): QuestionModel = with(input) {
        QuestionModel(id, name, imageUrl, category, difficulty, explanation)
    }

    override suspend fun mapFrom(input: QuestionModel): QuestionDataEntity = with(input) {
        QuestionDataEntity(id, name, imageUrl, category, difficulty, explanation)
    }
}