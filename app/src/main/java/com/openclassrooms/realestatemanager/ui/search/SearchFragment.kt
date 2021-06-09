package com.openclassrooms.realestatemanager.ui.search

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchBinding
import com.openclassrooms.realestatemanager.utils.Constants.PERIODS
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : DialogFragment(R.layout.fragment_search) {

    private val viewModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding.apply {
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, PERIODS)
            (searchPeriodLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)

            clearSearch.setOnClickListener { viewModel.clear() }

            searchType.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1) {
                    val chip: Chip = group.findViewById(checkedId)
                    viewModel.type = chip.text.toString()
                } else {
                    viewModel.type = ""
                }
            }

            searchPhotos.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1) {
                    val chip: Chip = group.findViewById(checkedId)
                    viewModel.nbPhotos = chip.text.toString()
                } else {
                    viewModel.nbPhotos = ""
                }
            }

            searchLocation.addTextChangedListener { viewModel.zone = it.toString() }
            searchMinPrice.addTextChangedListener { viewModel.minPrice = it.toString() }
            searchMaxPrice.addTextChangedListener { viewModel.maxPrice = it.toString() }
            searchStatus.setOnCheckedChangeListener { _, isChecked ->
                viewModel.status = isChecked
            } //TODO Add a text
            searchMinSurface.addTextChangedListener { viewModel.minSurface = it.toString() }
            searchMaxSurface.addTextChangedListener { viewModel.maxSurface = it.toString() }
            searchNearest.addTextChangedListener { viewModel.nearest = it.toString() }
            searchPeriodNumber.addTextChangedListener { viewModel.nbTime = it.toString() }
            searchPeriodLayout.editText?.addTextChangedListener {
                viewModel.unitTime = it.toString()
            }

            actionSearchQuery.setOnClickListener {
                /*
                searchMinPriceLayout.error = null
                searchMaxPriceLayout.error = null
                searchMinSurfaceLayout.error = null
                searchMaxSurfaceLayout.error = null
                searchPeriodLayout.error = null
                searchPeriodNumberLayout.error = null

                 */
                viewModel.onSearchClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchEvents.collect { event ->
                when (event) {
                    is SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage -> {
                        when (event.error) {
                            SearchViewModel.MESSAGE.MIN_PRICE -> {
                                binding.searchMinPriceLayout.error = event.error.msg
                            }
                            SearchViewModel.MESSAGE.MAX_PRICE -> {
                                binding.searchMaxPriceLayout.error = event.error.msg
                            }
                            SearchViewModel.MESSAGE.NUMBER_TIME -> {
                                binding.searchPeriodNumberLayout.error = event.error.msg
                            }
                            SearchViewModel.MESSAGE.UNIT_TIME -> {
                                binding.searchPeriodLayout.error = event.error.msg
                            }
                            SearchViewModel.MESSAGE.MIN_SURFACE -> {
                                binding.searchMinSurfaceLayout.error = event.error.msg
                            }
                            SearchViewModel.MESSAGE.MAX_SURFACE -> {
                                binding.searchMaxSurfaceLayout.error = event.error.msg
                            }
                        }
                    }

                    is SearchViewModel.SearchRealEstateEvent.Empty -> {
                    }
                }.exhaustive
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

}