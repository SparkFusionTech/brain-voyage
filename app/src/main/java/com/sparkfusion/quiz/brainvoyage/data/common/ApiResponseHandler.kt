package com.sparkfusion.quiz.brainvoyage.data.common

import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.UnexpectedException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NotFoundException
import retrofit2.Response

class ApiResponseHandler<R>(private val response: Response<R>) {

    fun handleFetchedData(): Answer<R> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) Answer.Failure(NotFoundException())
            else Answer.Success(body)
        } else {
            Answer.Failure(handleExceptionCode(response.code()))
        }
    }

    private fun handleExceptionCode(code: Int): BrainVoyageException {
        return when (code) {
            404 -> NotFoundException()
            else -> UnexpectedException()
        }
    }
}