package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.requests.NewRecordRequest
import com.disruptoroffice.financetracker.data.endpoints.requests.NewScheduledRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.NewRecordResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.NewScheduledResponse

class FinanceRecordRepository(private val httpClient: RetrofitClient) {

    suspend fun retrieveRecords(token: String, userId: String): SimpleResponse<List<FinanceRecordResponse>> {
        val apiService = httpClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.retrieveRecords(userId) }
    }

    suspend fun storeNewRecord(request: NewRecordRequest, token: String): SimpleResponse<NewRecordResponse> {
        val apiService = httpClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.storeRecord(request) }
    }

    suspend fun storeNewScheduledRecord(request: NewScheduledRequest, token: String): SimpleResponse<NewScheduledResponse> {
        val apiService = httpClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.storeSheduledRecord(request) }
    }
}