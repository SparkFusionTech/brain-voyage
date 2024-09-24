package com.sparkfusion.quiz.brainvoyage.utils.common

import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.DefaultDispatcher
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

@ViewModelScoped
class StringToMultipartWorker @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(value: String): RequestBody = withContext(defaultDispatcher) {
        value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}