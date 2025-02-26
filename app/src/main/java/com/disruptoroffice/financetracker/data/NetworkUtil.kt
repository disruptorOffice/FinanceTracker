package com.disruptoroffice.financetracker.data

import com.google.gson.Gson
import retrofit2.Response

class NetworkUtil {

    companion object {
        inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
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
}