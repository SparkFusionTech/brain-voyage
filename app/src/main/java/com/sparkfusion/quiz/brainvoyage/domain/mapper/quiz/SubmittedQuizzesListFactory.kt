package com.sparkfusion.quiz.brainvoyage.domain.mapper.quiz

import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.SubmittedQuizDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.SubmittedQuizModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class SubmittedQuizzesListFactory @Inject constructor(
    private val submittedQuizDataEntityFactory: SubmittedQuizDataEntityFactory
) : MapFactory<List<SubmittedQuizDataEntity>, List<SubmittedQuizModel>> {

    override suspend fun mapTo(input: List<SubmittedQuizDataEntity>): List<SubmittedQuizModel> {
        return input.map {
            submittedQuizDataEntityFactory.mapTo(it)
        }
    }

    override suspend fun mapFrom(input: List<SubmittedQuizModel>): List<SubmittedQuizDataEntity> {
        return input.map {
            submittedQuizDataEntityFactory.mapFrom(it)
        }
    }
}
