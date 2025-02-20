package com.disruptoroffice.financetracker.presentation.states

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
    data class ValidationError(val message: String) : LoginState()
}