package com.openclassrooms.realestatemanager.ui.loan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoanFragment : Fragment(R.layout.fragment_loan) {

    private val viewModel: LoanViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentLoanBinding.bind(view)

        binding.apply {
            actionCalculate.setOnClickListener {
                viewModel.onCalculate(
                    editContribution.text.toString(),
                    editDuration.text.toString(),
                    editRate.text.toString()
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is LoanViewModel.LoanUiState.Success -> {
                        binding.loanResult.text = uiState.value.toString()
                    }
                    is LoanViewModel.LoanUiState.Error -> {
                        binding.apply {
                            loanContributionLayout.error = uiState.contributionError
                            loanDuration.error = uiState.yearsError
                            loanRate.error = uiState.rateError
                        }
                    }
                }

            }
        }
    }
}