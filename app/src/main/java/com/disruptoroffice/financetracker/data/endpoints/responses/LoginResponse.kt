package com.disruptoroffice.financetracker.data.endpoints.responses

data class LoginResponse(
    val userId: Int,
    val token: String,
    val first_name: String,
    val last_name: String,
    val username: String,
)