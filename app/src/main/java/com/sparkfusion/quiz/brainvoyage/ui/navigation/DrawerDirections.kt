package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.MyQuizInfoScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.key.MY_QUIZ_ID_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.MyQuizzesScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.empty_loading.EmptyLoadingScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.settings.SettingsScreen
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundDarkColor
import com.sparkfusion.quiz.brainvoyage.ui.theme.settingsBackgroundLightColor

fun NavGraphBuilder.myQuizzesDirection(
    navController: NavController
) {
    composable<Destination.MyQuizzesScreenDestination> {
        MyQuizzesScreen(
            onBackClick = { navController.popBackStack() },
            onQuizClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set(MY_QUIZ_ID_KEY, it)
                navController.navigate(Destination.MyQuizInfoScreenDestination)
            }
        )
    }
}

fun NavGraphBuilder.myQuizInfoDirection(
    navController: NavController
) {
    composable<Destination.MyQuizInfoScreenDestination> {
        val id = navController.previousBackStackEntry?.savedStateHandle?.get<Long>(MY_QUIZ_ID_KEY)
        id?.let {
            MyQuizInfoScreen(
                quizId = id,
                onBackClick = { navController.popBackStack() }
            )
        } ?: EmptyLoadingScreen(
            modifier = Modifier.background(
                Brush.verticalGradient(listOf(settingsBackgroundLightColor, settingsBackgroundDarkColor))
            )
        )
    }
}

fun NavGraphBuilder.settingsDirection(
    navController: NavController
) {
    composable<Destination.SettingsDestination> {
        SettingsScreen(
            onBackClick = { navController.popBackStack() },
            onLogoutClick = {

            }
        )
    }
}
























