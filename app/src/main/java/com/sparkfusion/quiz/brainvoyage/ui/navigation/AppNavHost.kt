package com.sparkfusion.quiz.brainvoyage.ui.navigation

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
        startDestination = startDestination
    ) {
        loginDirection(
            navigateToRegistrationScreen = {
                navController.navigate(Destination.RegistrationDestination)
            }
        )
        registrationDirection(navController)
        catalogDirection()
        catalogItemDirection()
        quizItemDirection()
        imageCropDirection(navController)
    }
}