package com.disruptoroffice.financetracker.data.endpoints.requests

/**
 * Created by Alberto Avantes on 01/01/2026.
 */
data class NewScheduledRequest(
    val amount: Double,
    val concept: String,
    val frequency_id: Int,
    val category_id: Int,
    val type_payment_id: Int,
    val billing_day: String,
    val user_id: Int
)