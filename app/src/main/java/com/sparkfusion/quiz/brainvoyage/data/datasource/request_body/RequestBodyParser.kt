package com.sparkfusion.quiz.brainvoyage.data.datasource.request_body

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RequestBodyParser @Inject constructor() {

    fun <T> parse(value: T): RequestBody {
        val json = Gson().toJson(value)
        return json.toRequestBody("application/json".toMediaTypeOrNull())
    }
}
