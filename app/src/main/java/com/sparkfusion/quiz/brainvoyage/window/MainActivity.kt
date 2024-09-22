package com.sparkfusion.quiz.brainvoyage.window

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sparkfusion.quiz.brainvoyage.ui.navigation.AppNavHost
import com.sparkfusion.quiz.brainvoyage.ui.navigation.Destination
import com.sparkfusion.quiz.brainvoyage.ui.theme.BrainVoyageTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            BrainVoyageTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        innerPadding = innerPadding,
                        navController = navController,
                        startDestination = Destination.LoginDestination
                    )
                }
            }
        }
    }
}