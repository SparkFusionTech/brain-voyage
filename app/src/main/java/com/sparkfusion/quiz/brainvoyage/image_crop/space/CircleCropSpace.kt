package com.sparkfusion.quiz.brainvoyage.image_crop.space

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import com.sparkfusion.quiz.brainvoyage.image_crop.view.CropSpaceBox
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType

@Composable
fun CircleCropSpace(
    modifier: Modifier,
    cropCircleDiameter: Float,
    image: ImageBitmap,
    onScaleChange: ((Float) -> Unit)? = null,
    onTransformChange: ((Offset) -> Unit)? = null
) {
    val minScale = calculateRectangleMinScale(image.width, image.height, cropCircleDiameter)
    val scale = remember { mutableFloatStateOf(1f) }

    CropSpaceBox(
        modifier = modifier,
        cropType = ImageCropType.CircleCrop,
        scale = scale,
        minScale = minScale,
        image = image,
        cropWidth = cropCircleDiameter,
        cropHeight = cropCircleDiameter,
        onScaleChange = onScaleChange,
        onTransformChange = onTransformChange
    )
}

private fun calculateRectangleMinScale(imageWidth: Int, imageHeight: Int, cropCircleDiameter: Float): Float {
    val minSide = minOf(imageWidth.toFloat(), imageHeight.toFloat())
    return cropCircleDiameter / minSide
}
