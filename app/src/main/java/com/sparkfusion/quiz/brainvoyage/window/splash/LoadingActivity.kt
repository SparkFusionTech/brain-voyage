package com.sparkfusion.quiz.brainvoyage.window.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sparkfusion.quiz.brainvoyage.ui.navigation.Destination
import com.sparkfusion.quiz.brainvoyage.window.MainActivity
import com.sparkfusion.quiz.brainvoyage.window.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class LoadingActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.initialState()
            SplashScreen(
                state = state,
                navigateTo = ::navigateTo
            )
        }
    }

    private fun navigateTo(destination: Destination) {
        val serializedDestination = Json.encodeToString(destination)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra(MainActivity.START_NAVIGATION_DESTINATION, serializedDestination)
        }
        startActivity(intent)
        finish()
    }
}
