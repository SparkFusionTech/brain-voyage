package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.image_crop.common.CROPPED_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_TYPE_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.common.setImageCropType
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add.AddQuizScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.add_question.screen.AddQuestionScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.questions.AddQuizWithQuestionScreen
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add.SharedQuizViewModel
import com.sparkfusion.quiz.brainvoyage.ui.viewmodel.add_quiz.add_question.shared.SharedQuestionsViewModel

fun NavGraphBuilder.addQuizDirection(navController: NavController) {
    composable<Destination.AddQuizDestination> { backStackEntry ->
        val sharedViewModel: SharedQuizViewModel = hiltViewModel(backStackEntry)
        AddQuizScreen(
            onBackClick = navController::popBackStack,
            onNextClick = {
//                navController.currentBackStackEntry?.savedStateHandle?.set(SEND_QUIZ_KEY, it)
                navController.navigate(Destination.AddQuizWithQuestionsDestination)
            },
            onSearchImageScreenNavigate = {
                navController.navigate(Destination.ImageSearchDestination)
            },
            onImageCropNavigate = { bitmap ->
                navController.currentBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.currentBackStackEntry?.savedStateHandle?.setImageCropType(
                    IMAGE_CROP_TYPE_KEY,
                    ImageCropType.DynamicRectangleCrop(180.dp, 200.dp)
                )
                navController.navigate(Destination.ImageCropDestination)
            },
            getCroppedImageBitmap = {
                navController.currentBackStackEntry?.savedStateHandle?.get(CROPPED_KEY)
            },
            sharedQuizViewModel = sharedViewModel
        )
    }
}

fun NavGraphBuilder.addQuizWithQuestionsDirection(navController: NavController) {
    composable<Destination.AddQuizWithQuestionsDestination> { currentBackStack ->
        val backStack = navController.previousBackStackEntry
//        val model = navController.previousBackStackEntry?.savedStateHandle?.get<AddQuizInitialModel>(
//                SEND_QUIZ_KEY
//            )
//        if (model != null) {

        val sharedQuestionsViewModel: SharedQuestionsViewModel = hiltViewModel(currentBackStack)
            AddQuizWithQuestionScreen(
//                model = model,
                sharedQuizViewModel = if (backStack == null) hiltViewModel<SharedQuizViewModel>()
                else hiltViewModel(backStack),
                sharedQuestionsViewModel = sharedQuestionsViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onAddQuestionClick = {
                    navController.navigate(Destination.AddQuestionScreenDestination)
                }
            )
//        }
    }
}

fun NavGraphBuilder.addQuestionScreenDirection(navController: NavController) {
    composable<Destination.AddQuestionScreenDestination> {
        val previousBackStack = navController.previousBackStackEntry
        AddQuestionScreen(
            sharedQuestionsViewModel = if (previousBackStack == null) hiltViewModel()
            else hiltViewModel(previousBackStack),
            onDismiss = {
                navController.popBackStack()
            },
            onSaveQuestion = {
//                navController.currentBackStackEntry?.savedStateHandle?.set(SEND_QUESTION_KEY, it)
                navController.popBackStack()
            },
            onSearchImageScreenNavigate = {
                navController.navigate(Destination.ImageSearchDestination)
            },
            onImageCropNavigate = { bitmap ->
                navController.currentBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.currentBackStackEntry?.savedStateHandle?.setImageCropType(
                    IMAGE_CROP_TYPE_KEY, ImageCropType.RectangleCrop
                )
                navController.navigate(Destination.ImageCropDestination)
            },
            onReadCroppedImage = {
                navController.currentBackStackEntry?.savedStateHandle?.get(CROPPED_KEY)
            }
        )
    }
}
















