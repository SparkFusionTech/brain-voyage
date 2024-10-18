package com.sparkfusion.quiz.brainvoyage.image_crop.common

import kotlinx.serialization.Serializable

@Serializable
sealed class ImageCropType {

    @Serializable
    data object CircleCrop : ImageCropType()

    @Serializable
    data object RectangleCrop : ImageCropType()
}
