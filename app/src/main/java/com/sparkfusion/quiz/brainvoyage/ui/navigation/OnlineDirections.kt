package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.games.OnlineGamesScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.key.TIME_LEFT_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.questions.OnlineQuestionScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.selection.OnlineSelectionScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.victory.OnlineVictoryScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.online.waiting.WaitingOpponentAnswerScreen
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.online.Online2VS2GameViewModel

fun NavGraphBuilder.onlineGamesDirection(navController: NavController) {
    composable<OnlineGamesDestination> {
        OnlineGamesScreen(
            onSelectionScreenNavigate = {
                navController.navigate(OnlineSelectionDestination)
            },
            onBackClick = navController::popBackStack
        )
    }
}

fun NavGraphBuilder.onlineSelectionDirection(navController: NavController) {
    composable<OnlineSelectionDestination> {
        val viewModel = hiltViewModel<Online2VS2GameViewModel>(it)
        OnlineSelectionScreen(
            viewModel = viewModel,
            onGameStartScreenNavigate = {
                navController.navigate(OnlineQuestionDestination)
            },
            onBackClick = navController::popBackStack
        )
    }
}

fun NavGraphBuilder.onlineQuestionDirection(navController: NavController) {
    composable<OnlineQuestionDestination> {
        val backStackEntry = try {
            navController.getBackStackEntry(OnlineSelectionDestination)
        } catch (_: IllegalArgumentException) {
            null
        }

        if (backStackEntry != null) {
            val viewModel = hiltViewModel<Online2VS2GameViewModel>(backStackEntry)
            OnlineQuestionScreen(
                viewModel = viewModel,
                onFinishGame = {
                    navController.popBackStack(OnlineGamesDestination, true)
                },
                onVictoryScreenNavigate = {
                    navController.navigate(OnlineVictoryDestination)
                },
                onWaitingOpponentScreenNavigate = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(TIME_LEFT_KEY, viewModel.answerTimeLeft)
                    navController.navigate(OnlineWaitingOpponentAnswerDestination)
                }
            )
        }
    }
}

fun NavGraphBuilder.onlineVictoryDirection(navController: NavController) {
    composable<OnlineVictoryDestination> {
        val backStackEntry = try {
            navController.getBackStackEntry(OnlineSelectionDestination)
        } catch (_: IllegalArgumentException) {
            null
        }

        if (backStackEntry != null) {
            val viewModel = hiltViewModel<Online2VS2GameViewModel>(backStackEntry)
            OnlineVictoryScreen(
                viewModel = viewModel,
                onBackClick = {
                    navController.popBackStack(OnlineGamesDestination, true)
                }
            )
        }
    }
}

fun NavGraphBuilder.onlineWaitingOpponentAnswerDirection(navController: NavController) {
    composable<OnlineWaitingOpponentAnswerDestination> {
        val backStackEntry = try {
            navController.getBackStackEntry(OnlineSelectionDestination)
        } catch (_: IllegalArgumentException) {
            null
        }
        val timeLeft = navController.previousBackStackEntry?.savedStateHandle?.get<Long>(TIME_LEFT_KEY)

        if (backStackEntry != null && timeLeft != null) {
            val viewModel = hiltViewModel<Online2VS2GameViewModel>(backStackEntry)
            WaitingOpponentAnswerScreen(
                viewModel = viewModel,
                timeLeft = timeLeft,
                onBackClick = navController::popBackStack,
                onFinishGame = {
                    navController.popBackStack(OnlineGamesDestination, true)
                }
            )
        }
    }
}




















