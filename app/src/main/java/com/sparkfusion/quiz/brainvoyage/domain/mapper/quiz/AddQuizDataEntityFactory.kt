package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.AddQuizDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.AddQuizModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class AddQuizDataEntityFactory @Inject constructor() : MapFactory<AddQuizDataEntity, AddQuizModel> {

    override suspend fun mapTo(input: AddQuizDataEntity): AddQuizModel = with(input) {
        AddQuizModel(title, description, questions, catalogId)
    }

    override suspend fun mapFrom(input: AddQuizModel): AddQuizDataEntity = with(input) {
        AddQuizDataEntity(title, description, questions, catalogId)
    }
}
