package com.disruptoroffice.financetracker.data.endpoints

import com.disruptoroffice.financetracker.data.endpoints.requests.LoginRequest
import com.disruptoroffice.financetracker.data.endpoints.requests.NewRecordRequest
import com.disruptoroffice.financetracker.data.endpoints.requests.RegisterRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.LoginResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.NewRecordResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.RegisterResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypeCategoryResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypePaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("/v1/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("v1/users")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    @GET("v1/users/{user_id}/finances")
    suspend fun retrieveRecords(@Path("user_id") userId: String): Response<List<FinanceRecordResponse>>

    @GET("v1/type_payments")
    suspend fun typePayments(): Response<List<TypePaymentResponse>>

    @GET("v1/categories")
    suspend fun typeCategories(): Response<List<TypeCategoryResponse>>

    @POST("v1/finances")
    suspend fun storeRecord(@Body request: NewRecordRequest): Response<NewRecordResponse>
}