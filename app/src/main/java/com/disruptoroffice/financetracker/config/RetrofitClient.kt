package com.disruptoroffice.financetracker.config

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(
    private val session: SessionPreferences
) {


    private val BASE_URL = "http://10.0.2.2:4000/"

    private fun createOkHttpClient(token: String?): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(CookieInterceptor(session))
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                token?.let {
                    requestBuilder.addHeader("Authorization", "Bearer $it")
                }

                chain.proceed(requestBuilder.build())
            }
            .authenticator(TokenAuthenticator(dataStoreManager = session, this))
            .readTimeout(900, TimeUnit.SECONDS)
            .connectTimeout(900, TimeUnit.SECONDS)
            .build()
    }

    private fun createRetrofit(token: String?): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(createOkHttpClient(token))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(serviceClass: Class<T>, token: String? = null): T {
        return createRetrofit(token).create(serviceClass)
    }
}