package com.disruptoroffice.financetracker.presentation.states

sealed class DashboardState<out T> {
    object Loading: DashboardState<Nothing>()
    data class Success<T>(val data: T): DashboardState<T>()
    data class Error(val message: String): DashboardState<Nothing>()
}