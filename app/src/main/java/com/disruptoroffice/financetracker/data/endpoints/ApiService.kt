package com.disruptoroffice.financetracker.data.endpoints

import com.disruptoroffice.financetracker.data.endpoints.requests.LoginRequest
import com.disruptoroffice.financetracker.data.endpoints.requests.RegisterRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.LoginResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("v1/users")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}