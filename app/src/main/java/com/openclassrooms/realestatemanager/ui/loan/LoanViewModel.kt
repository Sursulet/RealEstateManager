package com.openclassrooms.realestatemanager.ui.loan

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class LoanViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<LoanUiState>(LoanUiState.Success(0.0))
    val uiState = _uiState.asStateFlow()

    fun onCalculate(contribution: String, years: String, rate: String) {
        var hasError = false
        val errorState = LoanUiState.Error(
            contributionError = if(contribution.isBlank()) {
                hasError = true
                "Add contribution"
            } else null,
            yearsError = if(years.isBlank()) {
                hasError = true
                "Add years"
            } else null,
            rateError = if(rate.isBlank()) {
                hasError = true
                "Add rate"
            } else null
        )

        if (hasError) {
            _uiState.value = errorState
            return
        }

        val result =
            (contribution.toDouble() * rate.toDouble() / 12) / (1 - (1 + rate.toDouble() / 12).pow(-12 * years.toDouble()))
        _uiState.value = LoanUiState.Success(result)
    }

    sealed class LoanUiState {
        data class Success(val value: Double) : LoanUiState()
        data class Error(
            val contributionError: String?,
            val yearsError: String?,
            val rateError: String?
        ) : LoanUiState()
    }
}