package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.AddQuizScreen

fun NavGraphBuilder.addQuizDirection(navController: NavController) {
    composable<Destination.AddQuizDestination> {
        AddQuizScreen()
    }
}
