package com.disruptoroffice.financetracker.data.endpoints.responses

data class FinanceRecordResponse(
    val id: Int,
    val amount: Double,
    val description: String,
    val date: String,
    val type_amount: String,
    val type_payment: String,
    val category: String
)