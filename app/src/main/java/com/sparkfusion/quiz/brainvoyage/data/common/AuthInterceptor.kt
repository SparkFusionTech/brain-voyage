package com.sparkfusion.quiz.brainvoyage.data.common

import android.util.Log
import com.sparkfusion.quiz.brainvoyage.data.datastore.TokenCache
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenCache: TokenCache
) : Interceptor {

    private val noAuthRequired = listOf("/users/create", "/users/authentication", "/users/exists")

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (noAuthRequired.any { originalRequest.url.encodedPath.contains(it) }) {
            return chain.proceed(originalRequest)
        }

        val token = runBlocking { tokenCache.getToken() }
        Log.d("TAGTAG", "TOKEN - $token")
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val requestWithToken = originalRequest.newBuilder()
            .url(originalRequest.url)
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(requestWithToken)
    }
}
