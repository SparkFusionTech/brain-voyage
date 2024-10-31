package com.sparkfusion.quiz.brainvoyage.data.common

import android.util.Log
import androidx.paging.PagingSource
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.UnexpectedException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.AlreadyExistsException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NetworkException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NotFoundException
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
        Log.i("TAGTAG", "exception - " + exception.message.toString())
        handleApiException(exception)
    }
}

suspend fun <K : Any, T : Any> safePagingApiCall(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> PagingSource.LoadResult<K, T>
): PagingSource.LoadResult<K, T> = withContext(dispatcher) {
    try {
        call.invoke()
    } catch (exception: Exception) {
        PagingSource.LoadResult.Error(handleApiException(exception).exception)
    }
}

fun handleExceptionCode(code: Int): BrainVoyageException {
    return when (code) {
        404 -> NotFoundException()
        409 -> AlreadyExistsException()
        else -> UnexpectedException()
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
