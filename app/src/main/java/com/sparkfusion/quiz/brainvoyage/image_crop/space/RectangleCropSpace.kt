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
fun RectangleCropSpace(
    modifier: Modifier,
    cropWidth: Float,
    cropHeight: Float,
    image: ImageBitmap,
    onScaleChange: ((Float) -> Unit)? = null,
    onTransformChange: ((Offset) -> Unit)? = null
) {
    val initialScaleFactor = 1.5f
    val minScale = calculateMinScale(image.width, image.height, cropWidth, cropHeight)
    val scale = remember { mutableFloatStateOf(minScale * initialScaleFactor) }

    CropSpaceBox(
        modifier = modifier,
        cropType = ImageCropType.RectangleCrop,
        scale = scale,
        minScale = minScale,
        image = image,
        cropWidth = cropWidth,
        cropHeight = cropHeight,
        onScaleChange = onScaleChange,
        onTransformChange = onTransformChange
    )
}

private fun calculateMinScale(imageWidth: Int, imageHeight: Int, cropWidth: Float, cropHeight: Float): Float {
    val minWidthScale = cropWidth / imageWidth.toFloat()
    val minHeightScale = cropHeight / imageHeight.toFloat()
    return maxOf(minWidthScale, minHeightScale)
}