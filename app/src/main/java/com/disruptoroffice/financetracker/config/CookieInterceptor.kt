package com.disruptoroffice.financetracker.config

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor(private val dataStoreManager: SessionPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        // Obtener las cookies de la respuesta
        val cookies = response.headers("Set-Cookie")
        cookies.forEach { cookie ->
            if (cookie.startsWith("jwt=")) {
                val refreshToken = cookie.substringAfter("jwt=").substringBefore(";")
                runBlocking { dataStoreManager.saveRefreshToken(refreshToken) }
            }
        }
        return response
    }
}