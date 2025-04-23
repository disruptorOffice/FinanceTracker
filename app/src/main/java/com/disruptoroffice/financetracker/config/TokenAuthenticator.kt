package com.disruptoroffice.financetracker.config

import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val dataStoreManager: SessionPreferences,
    private val httpClient: RetrofitClient
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            if (responseCount(response) >= 2) return null // Evita bucles infinitos

            val refreshToken = runBlocking { dataStoreManager.refreshToken.first() }
            val newToken = runBlocking {
                try {
                    val apiService = httpClient.createService(ApiService::class.java)
                    val cookieHeader = "jwt=$refreshToken"
                    val refreshResponse = NetworkUtil.safeApiCall { apiService.refreshToken(cookieHeader) }
                    dataStoreManager.saveToken(refreshResponse.body.token)
                    refreshResponse.body.token
                } catch (e: Exception) {
                    e.printStackTrace()
                    null // Error al refrescar token
                }
            }

            return newToken?.let {
                response.request().newBuilder()
                    .header("Authorization", "Bearer $it")
                    .build()
            }
        }
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var currentResponse = response.priorResponse()
        while (currentResponse != null) {
            count++
            currentResponse = currentResponse.priorResponse()
        }
        return count
    }
}
