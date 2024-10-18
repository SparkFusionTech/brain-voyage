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

fun NavGraphBuilder.addQuizDirection(navController: NavController) {
    composable<Destination.AddQuizDestination> {
        AddQuizScreen(
            onBackClick = navController::popBackStack,
            onNextClick = {},
            onSearchImageScreenNavigate = {
                navController.navigate(Destination.ImageSearchDestination)
            },
            onImageCropNavigate = { bitmap ->
                navController.currentBackStackEntry?.savedStateHandle?.set(IMAGE_CROP_KEY, bitmap)
                navController.currentBackStackEntry?.savedStateHandle?.setImageCropType(
                    IMAGE_CROP_TYPE_KEY,
                    ImageCropType.DynamicRectangleCrop(200.dp, 240.dp)
                )
                navController.navigate(Destination.ImageCropDestination)
            },
            getCroppedImageBitmap = {
                navController.currentBackStackEntry?.savedStateHandle?.get(CROPPED_KEY)
            }
        )
    }
}
