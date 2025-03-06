package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.config.SessionPreferences
import com.disruptoroffice.financetracker.data.endpoints.requests.NewRecordRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.TypeCategoryResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypePaymentResponse
import com.disruptoroffice.financetracker.data.repositories.FinanceRecordRepository
import com.disruptoroffice.financetracker.data.repositories.TypeCategoryRepository
import com.disruptoroffice.financetracker.data.repositories.TypePaymentRepository
import com.disruptoroffice.financetracker.presentation.states.NewRecordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewRecordViewModel @Inject constructor(
    private val typePaymentRepository: TypePaymentRepository,
    private val categoryRepository: TypeCategoryRepository,
    private val session: SessionPreferences,
    private val financeRepository: FinanceRecordRepository
): ViewModel() {

    private var _typePayments = emptyList<TypePaymentResponse>()
    var typePayments = MutableStateFlow(emptyList<String>())

    private var _typeCategories = emptyList<TypeCategoryResponse>()
    var typeCategories = MutableStateFlow(emptyList<String>())

    private val _state = MutableStateFlow<NewRecordState>(NewRecordState.Idle)
    val state: StateFlow<NewRecordState>
        get() = _state

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

    fun storeRecord(
        concept: String,
        amount: Double,
        amountType: String,
        categoryType: String,
        paymentType: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = NewRecordState.Loading
            val payment = _typePayments.find { element -> element.name == paymentType }
            val category = _typeCategories.find { item -> item.name == categoryType }

            val errorMap = HashMap<String, String>()

            if (concept.isEmpty())
                errorMap["concept"] = "Campo obligatorio"

            if (amount == 0.0)
                errorMap["amount"] = "Campo obligatorio"

            if (payment == null)
                errorMap["categoryType"] = "Campo obligatorio"

            if (category == null)
                errorMap["paymentType"] = "Campo obligatorio"

            if (errorMap.size > 0) {
                _state.value = NewRecordState.ValidationErrorForm(errorMap)
                return@launch
            }

            val result = financeRepository.storeNewRecord(NewRecordRequest(
                amount = amount,
                concept = concept,
                type_amount = amountType,
                category_id = category!!.id,
                type_payment_id = payment!!.id
            ),
                session.sessionToken.first())

            if (result.isSuccessful)
                _state.value = NewRecordState.Success
            else
                _state.value = NewRecordState.Error(result.exception!!.message!!)

        }
    }

    fun resetState() {
        _state.value = NewRecordState.Idle
    }

}