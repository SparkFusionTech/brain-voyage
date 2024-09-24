package com.sparkfusion.quiz.brainvoyage.utils.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Dispatchers @Inject constructor(

    @IODispatcher
    private val _ioDispatcher: CoroutineDispatcher,

    @MainDispatcher
    private val _mainDispatcher: MainCoroutineDispatcher,

    @DefaultDispatcher
    private val _defaultDispatcher: CoroutineDispatcher
) {
    val defaultDispatcher: CoroutineDispatcher get() = _defaultDispatcher
    val ioDispatcher: CoroutineDispatcher get() = _ioDispatcher
    val mainDispatcher: MainCoroutineDispatcher get() = _mainDispatcher
}