package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.lifecycle.SavedStateHandle
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.ByteArrayOutputStream

data class AddQuizInitialModel(
    val bitmap: Bitmap,
    val title: String,
    val description: String,
    val tags: List<String>
) {

    fun toSerializedModel(): Serialized {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
        return Serialized(base64String, title, description, tags)
    }

    @Serializable
    data class Serialized(
        val bitmapBase64: String,
        val title: String,
        val description: String,
        val tags: List<String>
    ) {

        fun toAddQuizInitialModel(): AddQuizInitialModel? {
            val decodedBytes = Base64.decode(bitmapBase64, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                ?: return null

            return AddQuizInitialModel(bitmap, title, description, tags)
        }
    }
}

fun SavedStateHandle.setAddQuizInitialModel(key: String, model: AddQuizInitialModel) {
    val jsonString = Json.encodeToString(model.toSerializedModel())
    this[key] = jsonString
}

fun SavedStateHandle.getAddQuizInitialModel(key: String): AddQuizInitialModel? {
    val jsonString = this.get<String>(key)
    val serialized: AddQuizInitialModel.Serialized? = jsonString?.let { Json.decodeFromString(it) }
    return serialized?.toAddQuizInitialModel()
}
