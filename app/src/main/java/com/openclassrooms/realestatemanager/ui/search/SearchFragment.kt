package com.openclassrooms.realestatemanager.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchBinding
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SearchFragment : DialogFragment(R.layout.fragment_search),
    AdapterView.OnItemSelectedListener {

    private val viewModel: SearchViewModel by viewModels()
    lateinit var binding: FragmentSearchBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding.apply {
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.periods,
                android.R.layout.simple_dropdown_item_1line
            ).also { ad ->
                ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                searchPeriod.apply {
                    adapter = ad
                }
            }

            searchType.setOnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1) {
                    val chip: Chip = group.findViewById(checkedId)
                    viewModel.type = chip.text.toString()
                }
            }

            searchPhotos.setOnCheckedChangeListener { group, checkedId ->
                Log.d(TAG, "SIZE: $checkedId")
                if (checkedId != -1) {
                    val chip: Chip = group.findViewById(checkedId)
                    viewModel.nbPhotos = chip.text.toString()
                }
            }

            searchLocation.addTextChangedListener { viewModel.zone = it.toString() }
            searchMinPrice.addTextChangedListener { viewModel.minPrice = it.toString() }
            searchMaxPrice.addTextChangedListener { viewModel.maxPrice = it.toString() }
            searchStatus.setOnCheckedChangeListener { _, isChecked ->
                viewModel.status = isChecked
            } //TODO Rajouter un text
            searchMinSurface.addTextChangedListener { viewModel.minSurface = it.toString() }
            searchMaxSurface.addTextChangedListener { viewModel.maxSurface = it.toString() }
            searchNearest.addTextChangedListener { viewModel.nearest = it.toString() }

            searchPeriodNumber.addTextChangedListener { viewModel.unit = it.toString() }
            searchPeriod.onItemSelectedListener = this@SearchFragment

            actionSearchQuery.setOnClickListener { viewModel.onSearchClick() }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.searchEvents.collect { event ->
                when(event) {
                    is SearchViewModel.SearchRealEstateEvent.ShowInvalidInputMessage -> {
                        binding.apply {
                            searchLocationLayout.error = event.msg
                        }
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is SearchViewModel.SearchRealEstateEvent.BackWithResult -> {
                        //TODO()
                        //dialog?.dismiss()
                    }
                }.exhaustive
            }
        }

        viewModel.current.observe(viewLifecycleOwner) { Log.d(TAG, "onViewCreated Current: $it") }

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val periods = resources.getStringArray(R.array.periods)
        val text = parent?.getItemAtPosition(position)
        Log.d(TAG, "onItemSelected: ${periods[position]}")
        Toast.makeText(requireContext(), "$text // $id", Toast.LENGTH_SHORT).show()
        viewModel.time = text.toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}