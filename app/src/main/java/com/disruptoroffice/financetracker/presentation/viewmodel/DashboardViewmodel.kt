package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.config.SessionPreferences
import com.disruptoroffice.financetracker.data.endpoints.responses.FinanceRecordResponse
import com.disruptoroffice.financetracker.data.repositories.FinanceRecordRepository
import com.disruptoroffice.financetracker.presentation.states.DashboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewmodel @Inject constructor(
    private val financeRepository: FinanceRecordRepository,
    private val session: SessionPreferences
): ViewModel() {

    private val _state = MutableStateFlow<DashboardState<List<FinanceRecordResponse>>>(DashboardState.Loading)
    val state: StateFlow<DashboardState<List<FinanceRecordResponse>>>
        get() = _state

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = financeRepository.retrieveRecords(session.sessionToken.first(), session.userId.first())

            if (result.isSuccessful) {
                if (result.body.isEmpty())
                    _state.value = DashboardState.Success(emptyList())
                else
                    _state.value = DashboardState.Success(result.body)
            } else
                _state.value = DashboardState.Error(result.exception!!.message!!)
        }
    }

}