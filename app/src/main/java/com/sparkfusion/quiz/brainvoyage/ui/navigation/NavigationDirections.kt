package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.ui.screen.CatalogItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.CatalogScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.LoginScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.QuizItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.RegistrationScreen

fun NavGraphBuilder.loginDirection(
    navigateToRegistrationScreen: () -> Unit
) {
    composable<Destination.LoginDestination> {
        LoginScreen(
            navigateToRegistrationScreen = navigateToRegistrationScreen
        )
    }
}

fun NavGraphBuilder.registrationDirection(onBackClick: () -> Unit) {
    composable<Destination.RegistrationDestination> {
        RegistrationScreen(
            onBackClick = onBackClick
        )
    }
}

fun NavGraphBuilder.catalogDirection() {
    composable<Destination.CatalogDestination> {
        CatalogScreen()
    }
}

fun NavGraphBuilder.catalogItemDirection() {
    composable<Destination.CatalogItemDestination> {
        CatalogItemScreen()
    }
}

fun NavGraphBuilder.quizItemDirection() {
    composable<Destination.QuizItemDestination> {
        QuizItemScreen()
    }
}