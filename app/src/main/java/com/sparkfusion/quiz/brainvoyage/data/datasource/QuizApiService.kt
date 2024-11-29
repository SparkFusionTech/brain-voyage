package com.sparkfusion.quiz.brainvoyage.data.datasource

import com.sparkfusion.quiz.brainvoyage.data.entity.QuizCatalogDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.GetQuizIdDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.GetQuizPreviewDataEntity
import com.sparkfusion.quiz.brainvoyage.data.entity.quiz.SubmittedQuizDataEntity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface QuizApiService {

    @GET("/catalog")
    suspend fun readCatalog(): Response<List<QuizCatalogDataEntity>>

    @GET("/quizzes/submitted")
    suspend fun readSubmittedQuizzes(): Response<List<SubmittedQuizDataEntity>>

    @Multipart
    @POST("/quizzes/create")
    suspend fun createQuiz(
        @Part("addQuizDto") addQuizDataEntity: RequestBody,
        @Part image: MultipartBody.Part
    ): Response<GetQuizIdDataEntity>

    @GET("/quizzes")
    suspend fun readQuizzesByCatalogId(
        @Query("catalogId") catalogId: Long
    ): Response<List<GetQuizPreviewDataEntity>>
    
    @GET("/quizzes/{quizId}")
    suspend fun readQuizById(
        @Path("quizId") quizId: Long
    ): Response<GetQuizPreviewDataEntity>

    @GET("/quizzes/{quizId}")
    suspend fun readSubmittedQuizById(
        @Path("quizId") quizId: Long
    ): Response<SubmittedQuizDataEntity>

    @DELETE("/quizzes/delete/{quizId}")
    suspend fun deleteQuizById(
        @Path("quizId") quizId: Long
    ): Response<Unit>
}



















