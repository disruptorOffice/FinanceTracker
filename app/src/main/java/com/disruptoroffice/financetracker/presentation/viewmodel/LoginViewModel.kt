package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.config.SessionPreferences
import com.disruptoroffice.financetracker.data.repositories.LoginRepository
import com.disruptoroffice.financetracker.presentation.states.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
private val loginRepository: LoginRepository,
    private val session: SessionPreferences
): ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState: StateFlow<LoginState>
        get() = _loginState

    init {
        viewModelScope.launch {
            if (session.isLoggedIng.first())
                _loginState.value = LoginState.Success
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.value = LoginState.Loading

            if (username.trim().isBlank() || password.trim().isBlank()) {
                _loginState.value = LoginState.Error("Todos los campos son obligatorios")
                return@launch
            }

            val result = loginRepository.login(username, password)
            if (result.isSuccessful) {
                session.storeSessionToken(result.body.token)
                _loginState.value = LoginState.Success
            }
            else
                _loginState.value = LoginState.Error(result.exception!!.message!!)
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }


}