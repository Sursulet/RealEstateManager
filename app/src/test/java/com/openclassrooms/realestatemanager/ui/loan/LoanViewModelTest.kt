package com.openclassrooms.realestatemanager.ui.loan

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.utilities.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoanViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: LoanViewModel

    @Before
    fun setUp() {
        viewModel = LoanViewModel()
    }

    @Test
    fun `calculate loan`() = testCoroutineRule.runBlockingTest {
        viewModel.onCalculate("150000", "20","5.7")
        val state = LoanViewModel.LoanUiState.Success(71250.0)
        assertThat(viewModel.uiState.value).isEqualTo(state)
    }

    @Test
    fun `calculate with empty years`() = testCoroutineRule.runBlockingTest {
        viewModel.onCalculate("150000", "","5.7")
        val state = LoanViewModel.LoanUiState.Error(null,"Add years", null)
        assertThat(viewModel.uiState.value).isEqualTo(state)
    }
}