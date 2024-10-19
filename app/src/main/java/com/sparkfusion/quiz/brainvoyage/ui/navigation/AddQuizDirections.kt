package com.sparkfusion.quiz.brainvoyage.ui.navigation

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sparkfusion.quiz.brainvoyage.image_crop.common.CROPPED_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.IMAGE_CROP_TYPE_KEY
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.common.setImageCropType
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.AddQuizScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.AddQuizWithQuestionScreen
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.SEND_QUIZ_KEY
import com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model.setAddQuizInitialModel

fun NavGraphBuilder.addQuizDirection(navController: NavController) {
    composable<Destination.AddQuizDestination> {
        AddQuizScreen(
            onBackClick = navController::popBackStack,
            onNextClick = {
                navController.currentBackStackEntry?.savedStateHandle?.setAddQuizInitialModel(SEND_QUIZ_KEY, it)
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
            }
        )
    }
}

fun NavGraphBuilder.addQuizWithQuestionsDirection(navController: NavController) {
    composable<Destination.AddQuizWithQuestionsDestination> {
        AddQuizWithQuestionScreen(

        )
    }
}


















