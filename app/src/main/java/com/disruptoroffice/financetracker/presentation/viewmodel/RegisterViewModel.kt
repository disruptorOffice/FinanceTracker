package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.data.repositories.RegisterRepository
import com.disruptoroffice.financetracker.presentation.states.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<RegisterState>(RegisterState.Idle)

    val state: StateFlow<RegisterState>
        get() = _uiState

    fun registerUser(firtsName: String, lastName: String, username: String, password: String) {
        _uiState.value = RegisterState.Loading
        if (firtsName.trim().isEmpty()
            || lastName.trim().isEmpty()
            || username.trim().isEmpty()
            || password.trim().isEmpty()) {
            _uiState.value = RegisterState.Error("Todos los campos son obligatorios")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = registerRepository.register(
                firtsName,
                lastName,
                username,
                password
            )

            if (result.isSuccessful) {
                _uiState.value = RegisterState.Success
            } else
                _uiState.value = RegisterState.Error(result.exception!!.message!!)


        }
    }

    fun resetState() {
        _uiState.value = RegisterState.Idle
    }

}