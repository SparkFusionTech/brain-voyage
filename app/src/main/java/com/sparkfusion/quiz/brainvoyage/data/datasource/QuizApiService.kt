package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.QuizCatalogDataEntity
import retrofit2.Response
import retrofit2.http.GET

interface QuizApiService {

    @GET("/catalog")
    suspend fun readCatalog(): Response<List<QuizCatalogDataEntity>>
}
