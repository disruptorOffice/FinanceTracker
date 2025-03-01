package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.config.SessionPreferences
import com.disruptoroffice.financetracker.data.endpoints.responses.TypeCategoryResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypePaymentResponse
import com.disruptoroffice.financetracker.data.repositories.TypeCategoryRepository
import com.disruptoroffice.financetracker.data.repositories.TypePaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecordViewModel @Inject constructor(
    private val typePaymentRepository: TypePaymentRepository,
    private val categoryRepository: TypeCategoryRepository,
    private val session: SessionPreferences
): ViewModel() {

    private var _typePayments = emptyList<TypePaymentResponse>()
    var typePayments = MutableStateFlow(emptyList<String>())

    private var _typeCategories = emptyList<TypeCategoryResponse>()
    var typeCategories = MutableStateFlow(emptyList<String>())

    init {
        retrieveTypePayments()
        retrieveTypeCatgories()
    }

    private fun retrieveTypePayments() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = typePaymentRepository.retrieveTyePayments(session.sessionToken.first())

            if (result.isSuccessful) {
                _typePayments = result.body
                typePayments.value = result.body.map { it.name }
            }
        }
    }

    private fun retrieveTypeCatgories() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = categoryRepository.retrieveTypeCategory(session.sessionToken.first())

            if (result.isSuccessful) {
                _typeCategories = result.body
                typeCategories.value = result.body.map { it.name }
            }
        }
    }

}