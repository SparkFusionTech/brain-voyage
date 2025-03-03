package com.sparkfusion.quiz.brainvoyage.utils.dispatchers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GlobalCoroutineScope

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopeModule {

    @Provides
    @Singleton
    @GlobalCoroutineScope
    fun provideGlobalCoroutineScope(
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(CoroutineName("Global coroutine") + SupervisorJob() + ioDispatcher)
    }
}
























