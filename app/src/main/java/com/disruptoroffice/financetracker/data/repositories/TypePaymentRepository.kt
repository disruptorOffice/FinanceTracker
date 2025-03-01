package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.responses.TypePaymentResponse

class TypePaymentRepository {
    suspend fun retrieveTyePayments(token: String): SimpleResponse<List<TypePaymentResponse>> {

        val apiService = RetrofitClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.typePayments() }
    }
}