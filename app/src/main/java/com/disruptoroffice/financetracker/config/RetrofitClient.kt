package com.disruptoroffice.financetracker.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private const val BASE_URL = "http://10.0.2.2:4000/" // Cambia esta URL por la de tu API

    private val retrofit: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .readTimeout(900, TimeUnit.SECONDS)
            .connectTimeout(900, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}