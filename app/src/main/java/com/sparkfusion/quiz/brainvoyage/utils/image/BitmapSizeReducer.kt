package com.sparkfusion.quiz.brainvoyage.utils.image

import android.graphics.Bitmap
import android.util.Log
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import kotlin.math.min
import kotlin.math.roundToInt

class BitmapSizeReducer @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend fun reduce(
        bitmap: Bitmap,
        maxScaleFactor: Float = Float.MAX_VALUE,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
        quality: Int = 85
    ): Bitmap = withContext(defaultDispatcher) {
        val scaleFactor = getScaleFactor(bitmap, maxScaleFactor)
        val compressedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width / scaleFactor).roundToInt(),
            (bitmap.height / scaleFactor).roundToInt(),
            true
        )

        val compressedStream = ByteArrayOutputStream()
        compressedBitmap.compress(format, quality, compressedStream)

        Log.d("ImageCompression", "SCALE: $scaleFactor")
        Log.d("ImageCompression", "BEFORE: ${bitmap.byteCount / 1024} KB")
        Log.d("ImageCompression", "AFTER: ${compressedBitmap.byteCount / 1024} KB")

        compressedBitmap
    }

    private suspend fun getScaleFactor(
        bitmap: Bitmap,
        maxScaleFactor: Float
    ): Float = withContext(defaultDispatcher) {
        val bitmapSizeInKB = bitmap.byteCount / 1024f
        val calculatedScaleFactor = when {
            bitmapSizeInKB <= MIN_SIZE_KB * 2 -> 1f
            bitmapSizeInKB <= MAX_SIZE_KB / 2 -> 1f + (bitmapSizeInKB - MIN_SIZE_KB) / (MAX_SIZE_KB / 2 - MIN_SIZE_KB)
            else -> bitmapSizeInKB / 1024f / 2f
        }

        min(maxScaleFactor, calculatedScaleFactor)
    }

    companion object {
        private const val MIN_SIZE_KB = 350f
        private const val MAX_SIZE_KB = 6000f
    }
}























