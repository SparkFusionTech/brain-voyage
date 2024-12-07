package com.sparkfusion.quiz.brainvoyage.window.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sparkfusion.quiz.brainvoyage.ui.navigation.Destination
import com.sparkfusion.quiz.brainvoyage.window.viewmodel.SplashContract
import com.sparkfusion.quiz.brainvoyage.window.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navigateTo: (Destination) -> Unit,
) {
    val state by viewModel.initialState().collectAsStateWithLifecycle()
    val isAnimationFinished = remember { mutableStateOf(false) }
    SplashContent(
        modifier = modifier,
        isAnimationFinished = isAnimationFinished
    )

    if (isAnimationFinished.value) {
        if (state == SplashContract.SplashState.Success) navigateTo(Destination.CatalogDestination)
        else if (state == SplashContract.SplashState.Error) navigateTo(Destination.LoginDestination)
    }
}
