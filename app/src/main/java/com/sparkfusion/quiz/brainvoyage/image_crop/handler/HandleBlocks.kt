package com.sparkfusion.quiz.brainvoyage.image_crop.handler

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntOffset
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType
import com.sparkfusion.quiz.brainvoyage.image_crop.common.alphaForegroundColor

fun DrawScope.drawBlock(
    image: ImageBitmap,
    canvas: Size
) {
    drawImage(
        image = image,
        dstOffset = IntOffset(
            x = canvas.width.div(2).minus(image.width.div(2)).toInt(),
            y = canvas.height.div(2).minus(image.height.div(2)).toInt()
        )
    )
}

fun DrawTransform.transformBlock(
    transform: Offset,
    scale: Float,
    canvas: Size
) {
    translate(
        left = transform.x,
        top = transform.y
    )
    scale(
        scaleX = scale,
        scaleY = scale,
        pivot = Offset(
            canvas.width.div(2),
            canvas.height.div(2)
        )
    )
}

fun transformHandlerBlock(
    transform: Offset,
    pan: Offset,
    scale: Float,
    image: ImageBitmap,
    cropWidth: Float,
    cropHeight: Float
): Offset {
    val scaledWidth = image.width * scale
    val scaledHeight = image.height * scale

    val transformXMax = (scaledWidth - cropWidth).coerceAtLeast(0f) / 2
    val transformYMax = (scaledHeight - cropHeight).coerceAtLeast(0f) / 2
    val transformXMin = -transformXMax
    val transformYMin = -transformYMax

    return Offset(
        (transform.x + pan.x).coerceIn(transformXMin, transformXMax),
        (transform.y + pan.y).coerceIn(transformYMin, transformYMax)
    )
}

fun DrawScope.drawBackgroundBlock(
    cropType: ImageCropType,
    cropWidth: Float,
    cropHeight: Float,
    canvas: Size
) {
    val nativeCanvas = drawContext.canvas.nativeCanvas
    val checkPoint = nativeCanvas.saveLayer(null, null)

    drawRect(alphaForegroundColor)
    if (cropType == ImageCropType.RectangleCrop) {
        drawRect(
            color = Color.Transparent,
            size = Size(cropWidth, cropHeight),
            topLeft = Offset(
                x = (canvas.width - cropWidth) / 2,
                y = (canvas.height - cropHeight) / 2
            ),
            blendMode = BlendMode.Clear
        )
    } else {
        drawCircle(
            color = Color.Transparent,
            radius = cropWidth / 2,
            center = Offset(
                x = canvas.width / 2,
                y = canvas.height / 2
            ),
            blendMode = BlendMode.Clear
        )
    }

    nativeCanvas.restoreToCount(checkPoint)
}