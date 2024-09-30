package com.sparkfusion.quiz.brainvoyage.window.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.sparkfusion.quiz.brainvoyage.ui.navigation.Destination
import com.sparkfusion.quiz.brainvoyage.window.viewmodel.SplashContract
import com.sparkfusion.quiz.brainvoyage.window.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateTo: (Destination) -> Unit,
) {
    val state = viewModel.initialState()
    val isAnimationFinished = remember { mutableStateOf(false) }
    SplashContent(isAnimationFinished = isAnimationFinished)

    if (isAnimationFinished.value) {
        if (state == SplashContract.SplashState.Success) navigateTo(Destination.CatalogDestination)
        else if (state == SplashContract.SplashState.Error) navigateTo(Destination.LoginDestination)
    }
}
