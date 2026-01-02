package com.disruptoroffice.financetracker.data.endpoints.responses

data class NewScheduledResponse(
    val id: Int,
    val amount: Double,
    val concept: String,
    val frequency_id: Int,
    val category_id: Int,
    val type_payment_id: Int,
    val billing_day: String,
    val user_id: Int,
    val updatedAt: String,
    val createdAt: String,
)
