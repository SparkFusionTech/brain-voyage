package com.sparkfusion.quiz.brainvoyage.utils.image

import android.graphics.Bitmap
import com.sparkfusion.quiz.brainvoyage.data.di.qualifier.CacheDirectory
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

@ViewModelScoped
class BitmapToFileWorker @Inject constructor(
    @CacheDirectory private val cacheDirectory: File,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        bitmap: Bitmap,
        child: ImageChildren,
        quality: Int = 100,
        format: Bitmap.CompressFormat = Bitmap.CompressFormat.PNG
    ): File = withContext(ioDispatcher) {
        try {
            val file = File(cacheDirectory, child.value).apply { createNewFile() }
            FileOutputStream(file).apply {
                bitmap.compress(format, quality, this)
                flush()
                close()
            }

            file
        } catch (exception: IOException) {
            throw FailedBitmapToFileConversionException()
        }
    }
}