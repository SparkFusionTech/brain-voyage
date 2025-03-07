package com.sparkfusion.quiz.brainvoyage.image_crop.common

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import kotlin.math.sqrt

class BitmapSizeReducer(
    private val bitmap: Bitmap
) {

    fun compressImageToTargetSize(
        targetSizeKB: Int = 600,
        minQuality: Int = 20
    ): Bitmap? {
        val targetSizeBytes = targetSizeKB * 1024
        var quality = 100
        val outputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        while (outputStream.size() > targetSizeBytes && quality > minQuality) {
            quality -= 5
            outputStream.reset()
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        if (outputStream.size() > targetSizeBytes) {
            val scaleFactor = calculateScaleFactor(outputStream.size(), targetSizeBytes)
            val newWidth = (bitmap.width * scaleFactor).toInt()
            val newHeight = (bitmap.height * scaleFactor).toInt()

            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
            outputStream.reset()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
        }

        val compressedBytes = outputStream.toByteArray()
        return BitmapFactory.decodeByteArray(compressedBytes, 0, compressedBytes.size)
    }

    private fun calculateScaleFactor(currentSize: Int, targetSize: Int): Float {
        return sqrt(targetSize.toFloat() / currentSize)
    }
}





















