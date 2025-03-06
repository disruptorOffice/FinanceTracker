package com.disruptoroffice.financetracker.presentation.states

sealed class NewRecordState {
    object Idle: NewRecordState()
    object Loading: NewRecordState()
    object Success: NewRecordState()
    data class Error(val message: String): NewRecordState()
    data class ValidationErrorForm(val errors: HashMap<String, String>): NewRecordState()
}