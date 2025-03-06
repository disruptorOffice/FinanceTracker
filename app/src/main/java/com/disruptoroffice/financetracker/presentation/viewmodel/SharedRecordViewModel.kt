package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedRecordViewModel: ViewModel() {

    private val _shouldRefresh = MutableStateFlow(false)
    val shouldRefresh: StateFlow<Boolean> = _shouldRefresh

    fun setRefresNeeded() {
        _shouldRefresh.value = true
    }

    fun resetRefresh() {
        _shouldRefresh.value = false
    }
}