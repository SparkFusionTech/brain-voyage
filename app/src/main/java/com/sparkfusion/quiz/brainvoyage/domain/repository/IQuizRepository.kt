package com.sparkfusion.quiz.brainvoyage.domain.repository

import com.sparkfusion.quiz.brainvoyage.domain.model.QuizCatalogModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.AddQuizModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizIdModel
import com.sparkfusion.quiz.brainvoyage.domain.model.quiz.GetQuizPreviewModel
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import okhttp3.MultipartBody

interface IQuizRepository {

    suspend fun readCatalog(): Answer<List<QuizCatalogModel>>

    suspend fun createQuiz(addQuizModel: AddQuizModel, image: MultipartBody.Part): Answer<GetQuizIdModel>

    suspend fun readQuizzesByCatalogId(catalogId: Long): Answer<List<GetQuizPreviewModel>>

    suspend fun readQuizById(quizId: Long): Answer<GetQuizPreviewModel>
}
