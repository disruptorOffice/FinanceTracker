package com.disruptoroffice.financetracker.presentation.composables

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Created by Alberto Avantes on 17/12/2025.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BillingDayPicker(
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val days = (1..31).toList()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = "Día $selectedDay",
            onValueChange = {},
            readOnly = true,
            label = { Text("Día de facturación") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            days.forEach { day ->
                DropdownMenuItem(
                    text = { Text("Día $day") },
                    onClick = {
                        onDaySelected(day)
                        expanded = false
                    }
                )
            }
        }
    }
}
