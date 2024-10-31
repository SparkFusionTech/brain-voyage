package com.sparkfusion.quiz.brainvoyage.domain.mapper.question

import com.sparkfusion.quiz.brainvoyage.data.entity.question.AddQuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.mapper.answer.AddAnswerDataEntityFactory
import com.sparkfusion.quiz.brainvoyage.domain.model.question.AddQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.MapFactory
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddQuestionDataEntityFactory @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val addAnswerDataEntityFactory: AddAnswerDataEntityFactory
) : MapFactory<AddQuestionDataEntity, AddQuestionModel> {

    override suspend fun mapTo(input: AddQuestionDataEntity): AddQuestionModel =
        withContext(defaultDispatcher) {
            return@withContext with(input) {
                val answersList = answers.map { addAnswerDataEntityFactory.mapTo(it) }
                AddQuestionModel(name, category, difficulty, explanation, quizId, answersList)
            }
        }

    override suspend fun mapFrom(input: AddQuestionModel): AddQuestionDataEntity =
        withContext(defaultDispatcher) {
            return@withContext with(input) {
                val answersList = answers.map { addAnswerDataEntityFactory.mapFrom(it) }
                AddQuestionDataEntity(name, category, difficulty, explanation, quizId, answersList)
            }
        }
}




















