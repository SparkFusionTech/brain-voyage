package com.sparkfusion.quiz.brainvoyage.utils.exception

import java.lang.Exception

open class BrainVoyageException(
    message: String? = null,
    cause: Throwable? = null
) : Exception(message ?: "Default application exception", cause)