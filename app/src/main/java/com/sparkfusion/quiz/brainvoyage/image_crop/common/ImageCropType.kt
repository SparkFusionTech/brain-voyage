package com.sparkfusion.quiz.brainvoyage.image_crop.common

sealed class ImageCropType {
    data object CircleCrop : ImageCropType()
    data object RectangleCrop : ImageCropType()
}