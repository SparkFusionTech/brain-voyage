package com.sparkfusion.quiz.brainvoyage.window

import android.app.Application
import androidx.work.Configuration
import com.sparkfusion.quiz.brainvoyage.data.datasource.workmanager.UserInfoWorkFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var userInfoWorkFactory: UserInfoWorkFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(userInfoWorkFactory)
            .build()
}
