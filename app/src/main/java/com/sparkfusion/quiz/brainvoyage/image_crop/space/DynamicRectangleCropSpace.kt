package com.sparkfusion.quiz.brainvoyage.image_crop.space

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.view.CropSpaceBox

@Composable
fun DynamicRectangleCropSpace(
    modifier: Modifier,
    cropWidthDp: Dp,
    cropHeightDp: Dp,
    image: ImageBitmap,
    onScaleChange: ((Float) -> Unit)? = null,
    onTransformChange: ((Offset) -> Unit)? = null
) {
    val density = LocalDensity.current
    val cropWidthPx = with(density) { cropWidthDp.toPx() }
    val cropHeightPx = with(density) { cropHeightDp.toPx() }

    val initialScaleFactor = 1.5f
    val minScale = calculateRectangleMinScale(image.width, image.height, cropWidthPx, cropHeightPx)
    val scale = remember { mutableFloatStateOf(minScale * initialScaleFactor) }

    CropSpaceBox(
        modifier = modifier,
        cropType = ImageCropType.RectangleCrop,
        scale = scale,
        minScale = minScale,
        image = image,
        cropWidth = cropWidthPx,
        cropHeight = cropHeightPx,
        onScaleChange = onScaleChange,
        onTransformChange = onTransformChange
    )
}
