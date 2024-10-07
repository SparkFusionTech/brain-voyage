package com.sparkfusion.quiz.brainvoyage.data.di

import com.sparkfusion.quiz.brainvoyage.data.repository.ImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.LoginRepository
import com.sparkfusion.quiz.brainvoyage.data.repository.QuizRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IImageSearchRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.IQuizRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindLoginRepositoryToILoginRepository(loginRepository: LoginRepository): ILoginRepository

    @Binds
    fun bindQuizRepositoryToIQuizRepository(quizRepository: QuizRepository): IQuizRepository

    @Binds
    fun bindImageSearchRepositoryToIImageSearchRepository(
        searchImageRepository: ImageSearchRepository
    ): IImageSearchRepository
}
