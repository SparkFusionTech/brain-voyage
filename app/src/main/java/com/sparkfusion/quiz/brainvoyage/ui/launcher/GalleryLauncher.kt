package com.sparkfusion.quiz.brainvoyage.ui.launcher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberLauncherForImageCropping(
    context: Context,
    navigateToImageCrop: (Bitmap) -> Unit
) = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { nullableUri ->
    nullableUri?.let { uri ->
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        navigateToImageCrop(bitmap)
    }
}