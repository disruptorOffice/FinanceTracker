package com.disruptoroffice.financetracker.presentation.states

/**
 * Created by Alberto Avantes on 14/12/2025.
 */
sealed class ScheduledState {
    object Idle: ScheduledState()
    object Loading: ScheduledState()
    data class Error(val message: String): ScheduledState()
    object Success: ScheduledState()
    data class ValidationErrorForm(val errors: HashMap<String, String>): ScheduledState()
    data class FrequenciesRetrieved(val frequencies: List<String>): ScheduledState()
    data class PaymentsRetrieved(val payments: List<String>): ScheduledState()
    data class CateogiresRetrieved(val categories: List<String>): ScheduledState()
}

data class ScheduledUiState(
    val frequencies: List<String> = emptyList(),
    val typePayments: List<String> = emptyList(),
    val typeCategories: List<String> = emptyList(),
    val isLoading: Boolean = true
)