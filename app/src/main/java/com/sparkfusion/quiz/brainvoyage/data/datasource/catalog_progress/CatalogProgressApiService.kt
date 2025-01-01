package com.sparkfusion.quiz.brainvoyage.data.datasource.catalog_progress

import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogExperienceDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogProgressDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.CatalogProgressQuestionDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.catalog_progress.UpdateCatalogProgressQuestionDataEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CatalogProgressApiService {

    @GET("/catalog/experience")
    suspend fun readCatalogExperience(): Response<CatalogExperienceDataEntity>

    @POST("/catalog/experience/update")
    suspend fun updateCatalogExperience(@Query("addXp") addXp: Int): Response<Unit>

    @GET("/catalog/progress")
    suspend fun readCatalogProgress(
        @Query("quizId") quizId: Long
    ): Response<CatalogProgressDataEntity>

    @POST("/catalog/progress/update")
    suspend fun updateCatalogProgress(
        @Query("quizId") quizId: Long
    ): Response<Unit>

    @GET("/catalog/progress/question")
    suspend fun readCatalogProgressQuestion(
        @Query("quizId") quizId: Long,
        @Query("questionId") questionId: Long
    ): Response<CatalogProgressQuestionDataEntity>

    @POST("/catalog/progress/question/update")
    suspend fun updateCatalogProgressQuestion(
        @Body updateCatalogProgressQuestionDataEntity: UpdateCatalogProgressQuestionDataEntity
    ): Response<Unit>
}






















