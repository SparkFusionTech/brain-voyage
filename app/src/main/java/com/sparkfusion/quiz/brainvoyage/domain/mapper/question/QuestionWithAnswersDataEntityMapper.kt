package com.sparkfusion.quiz.brainvoyage.domain.mapper.question

import com.sparkfusion.quiz.brainvoyage.data.entity.question.QuestionWithAnswersDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.mapper.answer.PlayAnswerListFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.question.QuestionWithAnswersModel
import com.sparkfusion.quiz.brainvoyage.ui.widget.star.QuestionDifficulty
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import javax.inject.Inject

class QuestionWithAnswersDataEntityMapper @Inject constructor(
    private val playAnswerListFactory: PlayAnswerListFactory
) : MapFactory<QuestionWithAnswersDataEntity, QuestionWithAnswersModel> {

    override suspend fun mapTo(input: QuestionWithAnswersDataEntity): QuestionWithAnswersModel =
        with(input) {
            QuestionWithAnswersModel(
                id,
                name,
                imageUrl,
                category,
                QuestionDifficulty.mapToQuestionDifficulty(difficulty),
                explanation,
                playAnswerListFactory.mapTo(answers)
            )
        }

    override suspend fun mapFrom(input: QuestionWithAnswersModel): QuestionWithAnswersDataEntity =
        with(input) {
            QuestionWithAnswersDataEntity(
                id,
                name,
                imageUrl,
                category,
                difficulty.mapToInt(),
                explanation,
                playAnswerListFactory.mapFrom(answers)
            )
        }
}





















