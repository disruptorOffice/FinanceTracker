package com.disruptoroffice.financetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.disruptoroffice.financetracker.config.SessionPreferences
import com.disruptoroffice.financetracker.data.endpoints.requests.NewScheduledRequest
import com.disruptoroffice.financetracker.data.endpoints.responses.FrequencyResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypeCategoryResponse
import com.disruptoroffice.financetracker.data.endpoints.responses.TypePaymentResponse
import com.disruptoroffice.financetracker.data.repositories.FinanceRecordRepository
import com.disruptoroffice.financetracker.data.repositories.FrequencyRepository
import com.disruptoroffice.financetracker.data.repositories.TypeCategoryRepository
import com.disruptoroffice.financetracker.data.repositories.TypePaymentRepository
import com.disruptoroffice.financetracker.presentation.states.ScheduledState
import com.disruptoroffice.financetracker.presentation.states.ScheduledUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Alberto Avantes on 14/12/2025.
 */

@HiltViewModel
class ScheduledViewModel @Inject constructor(
    private val session: SessionPreferences,
    private val frequencyRepository: FrequencyRepository,
    private val typePaymentRepository: TypePaymentRepository,
    private val categoryRepository: TypeCategoryRepository,
    private val financeRepository: FinanceRecordRepository,
): ViewModel() {

    private val _state = MutableStateFlow<ScheduledState>(ScheduledState.Idle)
    val state: StateFlow<ScheduledState> = _state

    private var _frequencies = emptyList<FrequencyResponse>()

    private var _typePayments = emptyList<TypePaymentResponse>()

    private var _typeCategories = emptyList<TypeCategoryResponse>()

    private val _mainState = MutableStateFlow(ScheduledUiState())
    val mainState: StateFlow<ScheduledUiState> = _mainState

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            coroutineScope {

                launch {
                    val result = frequencyRepository.retrieveFrequencies(session.sessionToken.first())
                    if (result.isSuccessful) {
                        _frequencies = result.body
                        _mainState.update {
                            it.copy(
                                frequencies = result.body.map { f -> f.frequency }
                            )
                        }
                    }
                }

                launch {
                    val result = typePaymentRepository.retrieveTyePayments(session.sessionToken.first())
                    if (result.isSuccessful) {
                        _typePayments = result.body
                        _mainState.update {
                            it.copy(
                                typePayments = result.body.map { p -> p.name }
                            )
                        }
                    }
                }

                launch {
                    val result = categoryRepository.retrieveTypeCategory(session.sessionToken.first())
                    if (result.isSuccessful) {
                        _typeCategories = result.body
                        _mainState.update {
                            it.copy(
                                typeCategories = result.body.map { c -> c.name }
                            )
                        }
                    }
                }
            }

            _mainState.update { it.copy(isLoading = false) }
        }
    }


    fun resetState() {
        _state.value = ScheduledState.Idle
    }

    fun storeRecord(
        concept: String,
        amount: String,
        amountType: String,
        frequencyType: String,
        categoryType: String,
        paymentType: String,
        dateRecord: Int,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = ScheduledState.Loading
            val payment = _typePayments.find { element -> element.name == paymentType }
            val category = _typeCategories.find { item -> item.name == categoryType }
            val frequency = _frequencies.find { freq -> freq.frequency == frequencyType }

            val errorMap = HashMap<String, String>()

            if (concept.isEmpty())
                errorMap["concept"] = "Campo obligatorio"

            if (amount.isEmpty())
                errorMap["amount"] = "Campo obligatorio"

            if (payment == null)
                errorMap["categoryType"] = "Campo obligatorio"

            if (frequency == null)
                errorMap["frequencyType"] = "Campo obligatorio"

            if (category == null)
                errorMap["paymentType"] = "Campo obligatorio"

            if (errorMap.isNotEmpty()) {
                _state.value = ScheduledState.ValidationErrorForm(errorMap)
                return@launch
            }

            val result = financeRepository.storeNewScheduledRecord(NewScheduledRequest(
                amount = amount.toDouble(),
                concept = concept,
                frequency_id = frequency!!.id,
                category_id = category!!.id,
                type_payment_id = payment!!.id,
                billing_day = dateRecord.toString(),
                user_id = session.userId.first().toInt()
            ),
                session.sessionToken.first())

            if (result.isSuccessful)
                _state.value = ScheduledState.Success
            else
                _state.value = ScheduledState.Error(result.exception!!.message!!)

        }
    }
}