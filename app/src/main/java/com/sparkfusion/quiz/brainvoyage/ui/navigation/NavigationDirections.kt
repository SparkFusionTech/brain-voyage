package com.sparkfusion.quiz.brainvoyage.ui.navigation

import android.graphics.Bitmap
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.sparkfusion.quiz.brainvoyage.ui.screen.quiz_item.QuizItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog.CatalogScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.CatalogItemScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.catalog_item.QUIZ_ID_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.image.ImageSearchScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.image.key.CROP_IMAGE_TYPE_AFTER_SEARCH_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.model.LoginRegistrationData
import com.sparkfusion.quiz.brainvoyage.ui.screen.login.LoginScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.play.PlayQuizScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.registration.RegistrationScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.victory.VictoryQuizScreen
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.victory.VictoryQuizViewModel

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
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        QUIZ_CATALOG_INFO_KEY,
                        it
                    )
                    navController.navigate(Destination.AddQuizDestination)
                },
                onQuizClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(QUIZ_ID_KEY, it)
                    navController.navigate(Destination.QuizItemDestination)
                },
                onNavigateToMyQuizzesScreen = {
                    navController.navigate(Destination.MyQuizzesScreenDestination)
                }
            )
        }
    }
}

fun NavGraphBuilder.quizItemDirection(navController: NavController) {
    composable<Destination.QuizItemDestination> {
        val quizId = navController.previousBackStackEntry?.savedStateHandle?.get<Long>(QUIZ_ID_KEY)
        quizId?.let {
            QuizItemScreen(
                quizId = it,
                onPlayButtonClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(QUIZ_ID_KEY, quizId)
                    navController.navigate(Destination.PlayQuizDestination)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

fun NavGraphBuilder.playQuizDirection(navController: NavController) {
    composable<Destination.PlayQuizDestination> { backStackEntry ->
        val quizId = navController.previousBackStackEntry?.savedStateHandle?.get<Long>(QUIZ_ID_KEY)
        val victoryQuizViewModel: VictoryQuizViewModel = hiltViewModel(backStackEntry)

        quizId?.let {
            PlayQuizScreen(
                quizId = it,
                victoryQuizViewModel = victoryQuizViewModel,
                onNavigateToVictoryScreen = {
                    navController.navigate(Destination.QuizVictoryDestination)
                },
                onDismiss = {
                    navController.popBackStack(Destination.CatalogItemDestination, inclusive = false)
                }
            )
        }
    }
}

fun NavGraphBuilder.quizVictoryDirection(navController: NavController) {
    composable<Destination.QuizVictoryDestination> {
        val backStack = navController.previousBackStackEntry
        val viewModel: VictoryQuizViewModel = if (backStack == null) hiltViewModel()
        else hiltViewModel(backStack)
        VictoryQuizScreen(
            viewModel = viewModel,
            onBackClick = {
                navController.popBackStack(Destination.QuizItemDestination, inclusive = true)
            }
        )
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
        val cropType = navController.previousBackStackEntry?.savedStateHandle?.getImageCropType(
            CROP_IMAGE_TYPE_AFTER_SEARCH_KEY
        )

        ImageSearchScreen(
            onNavigateBack = navController::popBackStack,
            onImageSelected = { bitmap ->
                navController.previousBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.previousBackStackEntry?.savedStateHandle?.setImageCropType(
                    IMAGE_CROP_TYPE_KEY, cropType ?: ImageCropType.RectangleCrop
                )

                navController.popBackStack()
                navController.navigate(Destination.ImageCropDestination)
            }
        )
    }
}












