package com.sparkfusion.quiz.brainvoyage.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.sparkfusion.quiz.brainvoyage.data.di.qualifier.CacheDirectory
import com.sparkfusion.quiz.brainvoyage.data.utils.DATA_STORE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = DATA_STORE_NAME
)

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @CacheDirectory
    @Provides
    fun provideCacheDirectory(@ApplicationContext context: Context): File {
        return context.cacheDir
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userDataStore
    }
}
