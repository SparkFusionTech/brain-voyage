package com.sparkfusion.quiz.brainvoyage.data.di

import com.sparkfusion.quiz.brainvoyage.data.LoginRepository
import com.sparkfusion.quiz.brainvoyage.domain.repository.ILoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindLoginRepositoryToILoginRepository(loginRepository: LoginRepository): ILoginRepository
}