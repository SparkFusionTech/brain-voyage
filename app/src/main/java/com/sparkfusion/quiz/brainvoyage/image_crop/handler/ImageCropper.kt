package com.sparkfusion.quiz.brainvoyage.image_crop.handler

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import com.sparkfusion.quiz.brainvoyage.image_crop.common.ImageCropType

class ImageCropper(
    private val cropType: ImageCropType,
    private val bitmap: ImageBitmap
) {

    fun cropImage(
        cropWidth: Float,
        cropHeight: Float,
        transform: Offset,
        scale: Float
    ): Bitmap {
        val scaledBitmap = createScaledBitmap(scale)
        val (cropX, cropY) = getCropCoordinates(scaledBitmap, cropWidth, cropHeight, transform)
        val croppedBitmap = if (cropType == ImageCropType.RectangleCrop || cropType is ImageCropType.DynamicRectangleCrop) {
            Bitmap.createBitmap(cropWidth.toInt(), cropHeight.toInt(), Bitmap.Config.ARGB_8888)
        } else {
            val cropDiameter = cropWidth.toInt()
            Bitmap.createBitmap(cropDiameter, cropDiameter, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(croppedBitmap)
        val paint = Paint().apply {
            isAntiAlias = true
        }

        val (srcRect, destRect) = if (cropType == ImageCropType.RectangleCrop || cropType is ImageCropType.DynamicRectangleCrop) {
            canvas.drawRect(0f, 0f, cropWidth, cropHeight, paint)
            getRectangles(cropWidth.toInt(), cropHeight.toInt(), cropX, cropY)
        } else {
            val halfOfDiameter = cropWidth / 2f
            canvas.drawCircle(halfOfDiameter, halfOfDiameter, halfOfDiameter, paint)
            getRectangles(cropWidth.toInt(), cropWidth.toInt(), cropX, cropY)
        }

        val maskPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN) }
        canvas.drawBitmap(scaledBitmap, srcRect, destRect, maskPaint)

        return croppedBitmap
    }

    private fun getRectangles(
        cropWidth: Int, cropHeight: Int, cropX: Int, cropY: Int
    ): Pair<Rect, Rect> {
        val srcRect = Rect(cropX, cropY, cropX + cropWidth, cropY + cropHeight)
        val destRect = Rect(0, 0, cropWidth, cropHeight)

        return Pair(srcRect, destRect)
    }

    private fun getCropCoordinates(
        scaledBitmap: Bitmap, cropWidth: Float, cropHeight: Float, transform: Offset
    ): Pair<Int, Int> {
        val cropX = (scaledBitmap.width / 2 - cropWidth / 2 - transform.x).toInt()
        val cropY = (scaledBitmap.height / 2 - cropHeight / 2 - transform.y).toInt()

        return Pair(cropX, cropY)
    }

    private fun createScaledBitmap(scale: Float): Bitmap {
        val matrix = Matrix().apply { postScale(scale, scale) }
        return Bitmap.createBitmap(
            bitmap.asAndroidBitmap(),
            0, 0,
            bitmap.width,
            bitmap.height,
            matrix, true
        )
    }
}