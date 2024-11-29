package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.MyQuizInfoScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quiz_info.key.MY_QUIZ_ID_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.drawer.my_quizzes.MyQuizzesScreen

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
        }
    }
}


























