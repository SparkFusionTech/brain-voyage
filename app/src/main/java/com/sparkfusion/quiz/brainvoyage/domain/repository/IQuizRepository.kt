package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer

interface IQuizRepository {

    suspend fun readCatalog(): Answer<List<QuizCatalogModel>>
}
