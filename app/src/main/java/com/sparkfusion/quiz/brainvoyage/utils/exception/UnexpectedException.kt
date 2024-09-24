package com.sparkfusion.quiz.brainvoyage.utils.exception

class UnexpectedException(
    cause: Throwable? = null
) : BrainVoyageException(cause?.message ?: "Occurred unknown exception", cause)