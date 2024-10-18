package com.sparkfusion.quiz.brainvoyage.image_crop.common

import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun SavedStateHandle.setImageCropType(key: String, cropType: ImageCropType) {
    val jsonString = Json.encodeToString(cropType)
    this[key] = jsonString
}

fun SavedStateHandle.getImageCropType(key: String): ImageCropType? {
    val jsonString = this.get<String>(key)
    return jsonString?.let { Json.decodeFromString(it) }
}
