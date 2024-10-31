package com.sparkfusion.quiz.brainvoyage.data.common

import android.util.Log
import com.sparkfusion.quiz.brainvoyage.utils.common.Answer
import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.network.NotFoundException
import retrofit2.Response

class ApiResponseHandler<R>(
    private val response: Response<R>,
    private val handleExceptionCode: (code: Int) -> BrainVoyageException
) {

    fun handleFetchedData(): Answer<R> {
        return if (response.isSuccessful) {
            val body = response.body()
            if (body == null) Answer.Failure(NotFoundException())
            else Answer.Success(body)
        } else {
            Log.i("TAGTAG", "" + response.code() + " - " + response.errorBody()?.string().toString())
            Answer.Failure(handleExceptionCode(response.code()))
        }
    }
}
