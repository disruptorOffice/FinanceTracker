package com.disruptoroffice.financetracker.presentation.composables

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Locale

/**
 * Created by Alberto Avantes on 12/10/2025.
 */

@Composable
fun DateTimePickerTextField(
    label: String = "Fecha y hora",
    dateTime: String,
    onDateTimeSelected: (String) -> Unit,
) {
    var checkState by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    LaunchedEffect(checkState) {
        if (!checkState)
            onDateTimeSelected("")
    }

    // Función que abre el TimePicker después del DatePicker
    fun showTimePicker(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            context,
            { _, selectedHour, selectedMinute ->
                // Guardamos la fecha y hora seleccionada
                val finalCalendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, selectedYear)
                    set(Calendar.MONTH, selectedMonth)
                    set(Calendar.DAY_OF_MONTH, selectedDay)
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                    set(Calendar.SECOND, 0)
                }

                // Formato final YYYY-MM-DD HH:mm:ss
                val formattedDateTime = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d %02d:%02d:%02d",
                    finalCalendar.get(Calendar.YEAR),
                    finalCalendar.get(Calendar.MONTH) + 1,
                    finalCalendar.get(Calendar.DAY_OF_MONTH),
                    finalCalendar.get(Calendar.HOUR_OF_DAY),
                    finalCalendar.get(Calendar.MINUTE),
                    finalCalendar.get(Calendar.SECOND)
                )


                onDateTimeSelected(formattedDateTime)
            },
            hour,
            minute,
            true // formato 24 horas
        )

        timePickerDialog.show()
    }

    // Función que abre el DatePicker primero
    fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                showTimePicker(selectedYear, selectedMonth, selectedDay)
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    // Colores dinámicos según el estado del checkbox
    val textFieldColors = TextFieldDefaults.colors(
        disabledLabelColor = if (checkState)
            MaterialTheme.colorScheme.primary
        else
            Color(0xFF737373),

        disabledIndicatorColor = if (checkState)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.inversePrimary,

        disabledTextColor = MaterialTheme.colorScheme.onSurface,
    )

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    enabled = checkState,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    showDatePicker()
                }
        ) {
            TextField(
                value = dateTime,
                onValueChange = {},
                label = { Text(label) },
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                colors = textFieldColors,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = checkState,
                onCheckedChange = {checkState = it},

                )
            Text("Usar una fecha diferente")
        }
    }
}
