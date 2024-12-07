package com.sparkfusion.quiz.brainvoyage.utils.dp

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import com.sparkfusion.quiz.brainvoyage.window.StatusBarHeightOwner

@Composable
fun getStatusBarHeightInDp(): Int {
    val configuration = LocalConfiguration.current
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) return 0

    val density = LocalDensity.current.density
    val statusBarHeightPx = StatusBarHeightOwner.height

    val statusBarHeightDp = (statusBarHeightPx / density).toInt()
    return statusBarHeightDp
}
