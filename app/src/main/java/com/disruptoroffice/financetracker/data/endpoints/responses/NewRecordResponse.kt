package com.disruptoroffice.financetracker.data.endpoints.responses

data class NewRecordResponse(
    val id: Int,
    val amount: Double,
    val concept: String,
    val type_amount: String,
    val category_id: Int,
    val type_payment_id: Int,
    val updatedAt: String,
    val createdAt: String,
)
