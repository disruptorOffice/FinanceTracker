package com.disruptoroffice.financetracker.core

import kotlinx.serialization.Serializable

@Serializable
//object Login
data class Login(val isRegisterCompleted: Boolean = false)

@Serializable
object Dashboard

@Serializable
object Register

@Serializable
object NewRecord

@Serializable
object Scheduled