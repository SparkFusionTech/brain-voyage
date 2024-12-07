package com.sparkfusion.quiz.brainvoyage.window

import android.view.View
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

fun ComponentActivity.hideBars() {
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
}

fun View.hasCutout(): Boolean {
    val insets = WindowInsetsCompat.toWindowInsetsCompat(rootWindowInsets)
    val cutoutInsets = insets.displayCutout
    return cutoutInsets != null && cutoutInsets.boundingRects.isNotEmpty()
}











