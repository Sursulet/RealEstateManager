package com.openclassrooms.realestatemanager.ui.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class LoanViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LoanUiState.Success(0.0))
    val uiState = _uiState.asStateFlow()

    fun onCalculate(contribution: String, years: String, rate: String) = viewModelScope.launch {
        val result =
            (contribution.toDouble() * rate.toDouble() / 12) / (1 - (1 + rate.toDouble() / 12).pow(-12 * years.toDouble()))
        _uiState.value = LoanUiState.Success(result)
    }

    sealed class LoanUiState {
        data class Success(val value: Double) : LoanUiState()
    }
}