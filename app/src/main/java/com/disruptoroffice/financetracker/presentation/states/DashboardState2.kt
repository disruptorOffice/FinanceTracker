package com.disruptoroffice.financetracker.presentation.states

/**
 * Created by Alberto Avantes on 04/10/2025.
 */
sealed class DashboardState2 {
    object Loading: DashboardState2()
    data class Error(val message: String): DashboardState2()
    object Idle: DashboardState2()
    data class UserData(val username: String): DashboardState2()
}