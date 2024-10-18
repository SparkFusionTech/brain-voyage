package com.sparkfusion.quiz.brainvoyage.ui.navigation

import android.graphics.Bitmap
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.image_crop.common.CROPPED_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_TYPE_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.common.getImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.common.setImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.screen.FailedOpenImageScreen
import com.sparkfusion.quiz.brainvoyage.image_crop.screen.ImageCropScreen
import com.sparkfusion.quiz.brainvoyage.ui.model.QUIZ_CATALOG_INFO_KEY
import com.sparkfusion.quiz.brainvoyage.ui.model.QuizCatalogSerializable
import com.sparkfusion.quiz.brainvoyage.ui.screen.QuizItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog.CatalogScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.CatalogItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.image.ImageSearchScreen
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

fun NavGraphBuilder.catalogDirection(navController: NavController) {
    composable<Destination.CatalogDestination> {
        CatalogScreen(
            onNavigateToCatalogItemScreen = {
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    QUIZ_CATALOG_INFO_KEY, it
                )
                navController.navigate(Destination.CatalogItemDestination)
            }
        )
    }
}

fun NavGraphBuilder.catalogItemDirection(navController: NavController) {
    composable<Destination.CatalogItemDestination> {
        val quizCatalogSerializableNullable = navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<QuizCatalogSerializable>(QUIZ_CATALOG_INFO_KEY)

        val currentDestination = navController.currentBackStackEntry?.destination
        if (quizCatalogSerializableNullable == null) {
            val catalogItemDestinationName =
                Destination.getDestinationRoute(Destination.CatalogItemDestination)
            if (currentDestination?.route == catalogItemDestinationName) {
                navController.popBackStack()
            }
        } else {
            CatalogItemScreen(
                quizCatalogSerializable = quizCatalogSerializableNullable,
                onNavigateToQuizAddScreen = {
                    navController.navigate(Destination.AddQuizDestination)
                }
            )
        }
    }
}

fun NavGraphBuilder.quizItemDirection() {
    composable<Destination.QuizItemDestination> {
        QuizItemScreen()
    }
}

fun NavGraphBuilder.imageCropDirection(navController: NavController) {
    composable<Destination.ImageCropDestination> {
        val savedState = navController.previousBackStackEntry?.savedStateHandle
        val bitmap: Bitmap? = savedState?.get<Bitmap>(IMAGE_CROP_KEY)
        val imageCropType: ImageCropType =
            savedState?.getImageCropType(IMAGE_CROP_TYPE_KEY) ?: ImageCropType.CircleCrop
        if (bitmap != null) {
            ImageCropScreen(
                cropType = imageCropType,
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

fun NavGraphBuilder.imageSearchDirection(navController: NavController) {
    composable<Destination.ImageSearchDestination> {
        ImageSearchScreen(
            onNavigateBack = navController::popBackStack,
            onImageSelected = { bitmap, width, height ->
                navController.previousBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.previousBackStackEntry?.savedStateHandle?.setImageCropType(
                    IMAGE_CROP_TYPE_KEY,
                    ImageCropType.DynamicRectangleCrop(width, height)
                )

                navController.popBackStack()
                navController.navigate(Destination.ImageCropDestination)
            }
        )
    }
}












