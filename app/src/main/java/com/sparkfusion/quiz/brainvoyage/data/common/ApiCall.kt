package com.sparkfusion.quiz.brainvoyage.data.common

import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.exception.UnexpectedException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> Answer<T>
): Answer<T> = withContext(dispatcher) {
    try {
        call.invoke()
    } catch (exception: Exception) {
        handleApiException(exception)
    }
}

private fun handleApiException(exception: Exception): Answer.Failure {
    return Answer.Failure(
        when (exception) {
            is IOException -> NetworkException(exception)
            else -> UnexpectedException(exception)
        }
    )
}