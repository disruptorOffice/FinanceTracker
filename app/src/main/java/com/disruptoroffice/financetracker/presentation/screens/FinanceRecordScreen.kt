package com.disruptoroffice.financetracker.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.disruptoroffice.financetracker.presentation.AmountType
import com.disruptoroffice.financetracker.presentation.states.NewRecordState
import com.disruptoroffice.financetracker.presentation.viewmodel.NewRecordViewModel
import com.disruptoroffice.financetracker.presentation.viewmodel.SharedRecordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceRecordScreen(
    viewModel: NewRecordViewModel,
    sharedViewModel: SharedRecordViewModel,
    onNavigateToDashboard: () -> Unit,
) {

    val state by viewModel.state.collectAsState()
    val typePayments = viewModel.typePayments.collectAsState()
    val typeCategories = viewModel.typeCategories.collectAsState()

    var concept by remember { mutableStateOf("") }
    var amount by remember { mutableDoubleStateOf(0.0) }
    var isExpanded by remember { mutableStateOf(false) }
    var isCategoryExpanded by remember { mutableStateOf(false) }
    var isPaymentExpanded by remember { mutableStateOf(false) }
    var amountType by remember { mutableStateOf(AmountType.EXPENSE.value) }
    var categoryType by remember { mutableStateOf("") }
    var paymentType by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.retrieveTypePayments()
        viewModel.retrieveTypeCatgories()
    }

    LaunchedEffect(state) {
        if (state is NewRecordState.Success) {
            sharedViewModel.setRefresNeeded()
            onNavigateToDashboard()
        }
    }

    if (state is NewRecordState.Error)
        ErrorDialog(
            (state as NewRecordState.Error).message,
            onDismissRequest =  { viewModel.resetState() },
            onConfirmButton = { viewModel.resetState() })


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it}
        ) {
            TextField(
                value = amountType,
                onValueChange = { amountType = it},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false}
            ) {
                DropdownMenuItem(
                    text = { Text(AmountType.EXPENSE.value) },
                    onClick = {
                        amountType = AmountType.EXPENSE.value
                        isExpanded = false
                    }
                )

                DropdownMenuItem(
                    text = { Text(AmountType.INCOME.value) },
                    onClick = {
                        amountType = AmountType.INCOME.value
                        isExpanded = false
                    }
                )
            }
        }
        Spacer(modifier =  Modifier.height(32.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = concept,
            onValueChange = { concept = it },
            label = { Text("Concepto") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )

        )
        Spacer(modifier =  Modifier.height(32.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amount.toString(),
            onValueChange = { amount = it.toDouble() },
            label = { Text("Cantidad") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Decimal
            )
        )
        Spacer(modifier =  Modifier.height(32.dp))

        ExposedDropdownMenuBox(
            expanded = isCategoryExpanded,
            onExpandedChange = { isCategoryExpanded = it}
        ) {
            TextField(
                label = { Text("Categoria") },
                value = categoryType,
                onValueChange = { categoryType = it},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isCategoryExpanded,
                onDismissRequest = { isCategoryExpanded = false}
            ) {
                typeCategories.value.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            categoryType = it
                            isCategoryExpanded = false
                        }
                    )

                }
            }
        }
        Spacer(modifier =  Modifier.height(32.dp))

        ExposedDropdownMenuBox(
            expanded = isPaymentExpanded,
            onExpandedChange = { isPaymentExpanded = it}
        ) {
            TextField(
                label = { Text("Tipo de pago") },
                value = paymentType,
                onValueChange = { paymentType = it},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isPaymentExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = isPaymentExpanded,
                onDismissRequest = { isPaymentExpanded = false}
            ) {
                typePayments.value.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            paymentType = item
                            isPaymentExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier =  Modifier.height(40.dp))
        ElevatedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                viewModel.storeRecord(
                    concept,
                    amount,
                    amountType,
                    categoryType,
                    paymentType
                )
            },
        ) {
            Text("Guardar")
        }
    }
}

@Composable
private fun ErrorDialog(message: String,
                        onDismissRequest: () -> Unit,
                        onConfirmButton: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Error al iniciar sesion") },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirmButton) { Text("Aceptar") }
        }
    )
}