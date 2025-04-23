package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.ErrorResponse
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.requests.LoginRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.LoginResponse
import com.google.gson.Gson
import retrofit2.Response

class LoginRepository(private val httpClient: RetrofitClient) {

    suspend fun login(username: String, password: String): SimpleResponse<LoginResponse> {
        val request = LoginRequest(username = username, password = password)

        val apiService = httpClient.createService(ApiService::class.java)

        return safeApiCall { apiService.login(request) }

    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            val response = apiCall.invoke()
            if (response.isSuccessful) {
                SimpleResponse.success(response)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                SimpleResponse.failure(Exception(errorResponse.message))
            }
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}