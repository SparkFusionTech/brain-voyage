package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    innerPadding: PaddingValues,
    navController: NavHostController,
    startDestination: Destination
) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            fadeIn(
                animationSpec = tween(durationMillis = 700, delayMillis = 250)
            )
        },
        exitTransition = {
            fadeOut(
                animationSpec = tween(durationMillis = 700, delayMillis = 180)
            )
        },
        popEnterTransition = {
            fadeIn(
                animationSpec = tween(durationMillis = 700, delayMillis = 250)
            )
        },
        popExitTransition = {
            fadeOut(
                animationSpec = tween(durationMillis = 700, delayMillis = 180)
            )
        }
    ) {
        loginDirection(navController)
        registrationDirection(navController)
        catalogDirection(navController)
        catalogItemDirection(navController)
        quizItemDirection(navController)

        imageCropDirection(navController)
        imageSearchDirection(navController)

        addQuizDirection(navController)
        addQuizWithQuestionsDirection(navController)
        addQuestionScreenDirection(navController)

        myQuizzesDirection(navController)
        myQuizInfoDirection(navController)
        settingsDirection(navController)

        playQuizDirection(navController)
        quizVictoryDirection(navController)
    }
}