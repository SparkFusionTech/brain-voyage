package com.sparkfusion.quiz.brainvoyage.image_crop.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.handler.drawBackgroundBlock
import com.sparkfusion.quiz.brainvoyage.image_crop.handler.drawBlock
import com.sparkfusion.quiz.brainvoyage.image_crop.handler.transformBlock
import com.sparkfusion.quiz.brainvoyage.image_crop.handler.transformHandlerBlock

@Composable
fun CropSpaceBox(
    modifier: Modifier = Modifier,
    cropType: ImageCropType,
    scale: MutableFloatState,
    minScale: Float,
    image: ImageBitmap,
    cropWidth: Float,
    cropHeight: Float,
    onScaleChange: ((Float) -> Unit)? = null,
    onTransformChange: ((Offset) -> Unit)? = null
) {
    val transform = remember { mutableStateOf(Offset(0f, 0f)) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale.floatValue = (scale.floatValue * zoom).coerceIn(minScale, 10f)
                    transform.value = transformHandlerBlock(transform.value, pan, scale.floatValue, image, cropWidth, cropHeight)
                    onScaleChange?.invoke(scale.floatValue)
                    onTransformChange?.invoke(transform.value)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            withTransform(
                transformBlock = { transformBlock(transform.value, scale.floatValue, size) },
                drawBlock = { drawBlock(image, size) }
            )
            drawBackgroundBlock(cropType, cropWidth, cropHeight, size)
        }
    }
}