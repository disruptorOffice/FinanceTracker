package com.disruptoroffice.financetracker.data.endpoints.responses

data class LoginResponse(
    val userId: Int,
    val token: String
)