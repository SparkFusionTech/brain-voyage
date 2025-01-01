package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.UpdateCatalogProgressQuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogExperienceModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.CatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.domain.model.catalog_progress.UpdateCatalogProgressQuestionModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer

interface ICatalogProgressRepository {

    suspend fun readCatalogExperience(): Answer<CatalogExperienceModel>

    suspend fun updateCatalogExperience(addXp: Int): Answer<Unit>

    suspend fun readCatalogProgress(quizId: Long): Answer<CatalogProgressModel>

    suspend fun updateCatalogProgress(quizId: Long): Answer<Unit>

    suspend fun readCatalogProgressQuestion(
        quizId: Long,
        questionId: Long
    ): Answer<CatalogProgressQuestionModel>

    suspend fun updateCatalogProgressQuestion(
        updateCatalogProgressQuestionModel: UpdateCatalogProgressQuestionModel
    ): Answer<Unit>
}














