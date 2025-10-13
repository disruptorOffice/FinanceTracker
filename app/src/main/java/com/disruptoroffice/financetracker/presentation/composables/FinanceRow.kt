package com.disruptoroffice.financetracker.presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse

@Composable
fun FinanceRow(item: FinanceRecordResponse, onClickRow: (FinanceRecordResponse) -> Unit) {
    Box(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable {
                onClickRow(item)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = item.description,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1150AB)
                )
                Text(text = item.type_payment)
            }
            Spacer(Modifier.weight(1f))
            Text(text = when {
                item.type_amount == "income" -> {
                    item.amount.toString()
                }
                else -> {
                    "-${item.amount}"
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FinancePreview() {
    val item = FinanceRecordResponse(
        id = 1,
        amount = 30000.00,
        description = "Deposito",
        created_date = "2025-01-01 12:13:59",
//        type_amount ="income",
        type_amount ="expense",
        type_payment = "credit cart",
        category = "Food",
        "2024-12-01 12:13:59",
    )

    FinanceRow(item) {

    }
}