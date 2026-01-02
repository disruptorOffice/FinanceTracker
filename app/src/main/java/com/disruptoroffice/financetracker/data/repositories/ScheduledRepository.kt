package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.requests.NewRecordRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.NewRecordResponse

/**
 * Created by Alberto Avantes on 17/12/2025.
 */
class ScheduledRepository(private val httpClient: RetrofitClient) {

    suspend fun store(request: NewRecordRequest, token: String): SimpleResponse<NewRecordResponse> {
        val apiService = httpClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.storeRecord(request) }
    }
}