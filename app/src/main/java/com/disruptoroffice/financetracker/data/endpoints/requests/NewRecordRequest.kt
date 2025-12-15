package com.disruptoroffice.financetracker.data.endpoints.requests

data class NewRecordRequest (
    val amount: Double,
    val concept: String,
    val type_amount: String,
    val category_id: Int,
    val type_payment_id: Int,
    val date_record: String? = null,
)