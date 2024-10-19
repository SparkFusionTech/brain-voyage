package com.sparkfusion.quiz.brainvoyage.ui.screen.add_quiz.model

import android.graphics.Bitmap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

object BitmapSerializer : KSerializer<Bitmap> {

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("bitmap", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Bitmap) {
        val string = Json.encodeToString(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): Bitmap {
        return Json.decodeFromString(decoder.decodeString())
    }
}
