package com.sparkfusion.quiz.brainvoyage.image_crop.common

import androidx.compose.ui.unit.Dp
import kotlinx.serialization.Serializable

@Serializable
sealed class ImageCropType {

    @Serializable
    data object CircleCrop : ImageCropType()

    @Serializable
    data object RectangleCrop : ImageCropType()

    @Serializable
    data class DynamicRectangleCrop(
        @Serializable(with = DpSerializer::class) val widthDp: Dp,
        @Serializable(with = DpSerializer::class) val heightDp: Dp
    ) : ImageCropType()
}
