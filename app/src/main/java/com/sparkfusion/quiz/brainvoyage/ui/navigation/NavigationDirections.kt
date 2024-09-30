package com.sparkfusion.quiz.brainvoyage.ui.navigation

import android.graphics.Bitmap
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.image_crop.common.CROPPED_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.screen.FailedOpenImageScreen
import com.sparkfusion.quiz.brainvoyage.image_crop.screen.ImageCropScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.CatalogItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog.CatalogScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.QuizItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.LoginRegistrationData
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.LoginScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.RegistrationScreen

fun NavGraphBuilder.loginDirection(navController: NavController) {
    composable<Destination.LoginDestination> {
        LoginScreen(
            navigateToRegistrationScreen = {
                navController.navigate(Destination.RegistrationDestination)
            },
            navigateToQuizCatalogScreen = {
                navController.popBackStack()
                navController.navigate(Destination.CatalogDestination)
            },
            fetchRegistrationData = {
                Pair(
                    navController.currentBackStackEntry?.savedStateHandle?.get(
                        LoginRegistrationData.EMAIL.value
                    ),
                    navController.currentBackStackEntry?.savedStateHandle?.get(
                        LoginRegistrationData.PASSWORD.value
                    )
                )
            }
        )
    }
}

fun NavGraphBuilder.registrationDirection(navController: NavController) {
    composable<Destination.RegistrationDestination> {
        RegistrationScreen(
            onBackClick = navController::popBackStack,
            navigateToImageCrop = { bitmap ->
                navController.currentBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.navigate(Destination.ImageCropDestination)
            },
            getCroppedImageBitmap = {
                navController.currentBackStackEntry?.savedStateHandle?.get(CROPPED_KEY)
            },
            saveRegistrationDataAndExit = { email, password ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    LoginRegistrationData.EMAIL.value,
                    email
                )
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    LoginRegistrationData.PASSWORD.value,
                    password
                )
                navController.popBackStack()
            }
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

fun NavGraphBuilder.imageCropDirection(navController: NavController) {
    composable<Destination.ImageCropDestination> {
        val bitmap: Bitmap? =
            navController.previousBackStackEntry?.savedStateHandle?.get<Bitmap>(IMAGE_CROP_KEY)
        if (bitmap != null) {
            ImageCropScreen(
                cropType = ImageCropType.CircleCrop,
                bitmap = bitmap,
                onCropClickHandler = {
                    navController.previousBackStackEntry?.savedStateHandle?.set(CROPPED_KEY, it)
                    navController.popBackStack()
                }
            )
        } else {
            FailedOpenImageScreen()
        }
    }
}














