package com.sparkfusion.quiz.brainvoyage.window.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsCompat
import com.sparkfusion.quiz.brainvoyage.ui.navigation.Destination
import com.sparkfusion.quiz.brainvoyage.ui.theme.BrainVoyageTheme
import com.sparkfusion.quiz.brainvoyage.window.MainActivity
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner
import com.sparkfusion.quiz.brainvoyage.window.hasCutout
import com.sparkfusion.quiz.brainvoyage.window.hideBars
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class LoadingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hideBars()

        setContent {
            val view = LocalView.current

            setCutoutStatus(view)
            getStatusBarHeight()

            BrainVoyageTheme {
                SplashScreen(
                    modifier = Modifier.fillMaxSize(),
                    navigateTo = ::navigateTo
                )
            }
        }
    }

    private fun setCutoutStatus(view: View) {
        StatusBarHeightOwner.hasCutout = view.hasCutout()
    }

    private fun getStatusBarHeight(): Int {
        val insets = window.decorView.rootWindowInsets
        StatusBarHeightOwner.setHeightIfNotAlreadyInstalled(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    insets?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
                } else {
                    @Suppress("DEPRECATION")
                    insets?.systemWindowInsetTop ?: 0
                }
            } else {
                getStatusBarHeightOldVersion()
            }
        )

        return StatusBarHeightOwner.height
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    private fun getStatusBarHeightOldVersion(): Int {
        val resources = window.context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) resources.getDimensionPixelSize(resourceId) else 0
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
