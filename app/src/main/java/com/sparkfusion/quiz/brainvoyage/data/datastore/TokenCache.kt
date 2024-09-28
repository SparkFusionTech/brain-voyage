package com.sparkfusion.quiz.brainvoyage.data.datastore

import com.sparkfusion.quiz.brainvoyage.domain.repository.ISession
import com.sparkfusion.quiz.brainvoyage.utils.dispatchers.IODispatcher
import com.sparkfusion.quiz.brainvoyage.utils.image.FailedBitmapToFileConversionException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenCache @Inject constructor(
    private val session: ISession,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private var token: String? = null

    suspend fun getToken(): String? = withContext(ioDispatcher) {
        try {
            if (token == null) {
                token = session.readUserToken().firstOrNull()
            }

            token
        } catch (ignore: FailedBitmapToFileConversionException) {
            null
        }
    }
}