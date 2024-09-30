package com.sparkfusion.quiz.brainvoyage.data.common

import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NotFoundException
import retrofit2.Response

class ApiListResponseHandler<R>(
    private val response: Response<List<R>>,
    private val handleExceptionCode: (code: Int) -> BrainVoyageException
) {

    fun handleFetchedData(): Answer<List<R>> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) Answer.Failure(NotFoundException())
            else Answer.Success(body)
        } else {
            Answer.Failure(handleExceptionCode(response.code()))
        }
    }
}
