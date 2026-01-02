package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.responses.FrequencyResponse

/**
 * Created by Alberto Avantes on 14/12/2025.
 */
class FrequencyRepository (private val httpClient: RetrofitClient) {

    suspend fun retrieveFrequencies(token: String): SimpleResponse<List<FrequencyResponse>> {
        val apiService = httpClient.createService(ApiService::class.java, token)
        return NetworkUtil.safeApiCall { apiService.retrieveFrequencies() }
    }
}