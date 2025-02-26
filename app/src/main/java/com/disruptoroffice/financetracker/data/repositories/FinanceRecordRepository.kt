package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse

class FinanceRecordRepository {

    suspend fun retrieveRecords(token: String, userId: String): SimpleResponse<List<FinanceRecordResponse>> {
        val apiService = RetrofitClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.retrieveRecords(userId) }
    }
}