package com.sparkfusion.quiz.brainvoyage.utils.common

import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException
import com.sparkfusion.quiz.brainvoyage.utils.exception.answer.AnswerHasNoMapperException
import com.sparkfusion.quiz.brainvoyage.utils.exception.answer.AnswerMappingException

sealed class Answer<out T> {

    abstract fun unwrap(): T
    abstract suspend fun <R> suspendMap(mapper: (suspend (T) -> R)? = null): Answer<R>

    data class Success<out T>(private val value: T) : Answer<T>() {

        override fun unwrap(): T = value

        override suspend fun <R> suspendMap(mapper: (suspend (T) -> R)?): Answer<R> {
            return if (mapper == null) Failure(AnswerHasNoMapperException())
            else try {
                Success(mapper(unwrap()))
            } catch (e: Exception) {
                Failure(AnswerMappingException())
            }
        }
    }

    data class Failure(val exception: BrainVoyageException) : Answer<Nothing>() {

        override fun unwrap(): Nothing = throw exception

        override suspend fun <R> suspendMap(mapper: (suspend (Nothing) -> R)?): Answer<R> {
            return this
        }
    }

    inline fun onSuccess(action: (T) -> Unit): Answer<T> {
        if (this is Success) action(unwrap())
        return this
    }

    inline fun onFailure(action: (BrainVoyageException) -> Unit): Answer<T> {
        if (this is Failure) action(exception)
        return this
    }
}