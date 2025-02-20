package com.disruptoroffice.financetracker.data.endpoints

import com.disruptoroffice.financetracker.data.endpoints.requests.LoginRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}