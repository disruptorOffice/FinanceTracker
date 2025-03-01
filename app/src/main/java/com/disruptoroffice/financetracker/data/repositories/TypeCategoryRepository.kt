package com.disruptoroffice.financetracker.data.repositories

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.NetworkUtil
import com.disruptoroffice.financetracker.data.SimpleResponse
import com.disruptoroffice.financetracker.data.endpoints.ApiService
import com.disruptoroffice.financetracker.data.endpoints.responses.TypeCategoryResponse

class TypeCategoryRepository {

    suspend fun retrieveTypeCategory(token: String): SimpleResponse<List<TypeCategoryResponse>> {

        val apiService = RetrofitClient.createService(ApiService::class.java, token)

        return NetworkUtil.safeApiCall { apiService.typeCategories() }
    }
}