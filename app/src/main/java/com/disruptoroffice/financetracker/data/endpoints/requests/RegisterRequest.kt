package com.disruptoroffice.financetracker.data.endpoints.requests

data class RegisterRequest(
    val first_name: String,
    val last_name: String,
    val username: String,
    val password: String
)
