package com.sparkfusion.quiz.brainvoyage.data.di

import com.sparkfusion.quiz.brainvoyage.data.common.AuthInterceptor
import com.sparkfusion.quiz.brainvoyage.data.datasource.AnswerApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.image_search.ImageSearchApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.LoginApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuestionApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.QuizApiService
import com.sparkfusion.quiz.brainvoyage.data.datasource.TagApiService
import com.sparkfusion.quiz.brainvoyage.data.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(16, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoginApiService(retrofit: Retrofit): LoginApiService {
        return retrofit.create(LoginApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTagApiService(retrofit: Retrofit): TagApiService {
        return retrofit.create(TagApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideQuestionApiService(retrofit: Retrofit): QuestionApiService {
        return retrofit.create(QuestionApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAnswerApiService(retrofit: Retrofit): AnswerApiService {
        return retrofit.create(AnswerApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideQuizService(retrofit: Retrofit): QuizApiService {
        return retrofit.create(QuizApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideImageSearchApiService(retrofit: Retrofit): ImageSearchApiService {
        return retrofit.create(ImageSearchApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}
