package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.ErrorResponse
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.requests.RegisterRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.RegisterResponse
import com.google.gson.Gson
import retrofit2.Response

class RegisterRepository {

    suspend fun register(firtsName: String, lastName: String, username: String, password: String): SimpleResponse<RegisterResponse> {
        val request = RegisterRequest(
            first_name = firtsName,
            last_name = lastName,
            username = username,
            password = password
        )

        val apiService = RetrofitClient.createService(ApiService::class.java)

        return safeApiCall { apiService.register(request) }
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