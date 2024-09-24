package com.sparkfusion.quiz.brainvoyage.utils.exception.network

import com.sparkfusion.quiz.brainvoyage.utils.exception.BrainVoyageException

class NetworkException(cause: Throwable) : BrainVoyageException(cause.message, cause)