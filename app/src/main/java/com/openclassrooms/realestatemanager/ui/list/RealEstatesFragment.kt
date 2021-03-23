package com.openclassrooms.realestatemanager.ui.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstatesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstatesFragment : Fragment(R.layout.fragment_real_estates), OnRealEstateClickListener {

    private lateinit var realEstateClickListener: OnRealEstateClickListener

    private val viewModel: RealEstatesViewModel by viewModels()
    private lateinit var realEstateAdapter: RealEstatesAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        realEstateClickListener = (context as? OnRealEstateClickListener)
            ?: throw IllegalStateException("Activity should implement OnRealEstateClickListener")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentRealEstatesBinding.bind(view)

        realEstateAdapter = RealEstatesAdapter(this)

        binding.apply {
            recyclerviewRealEstates.apply {
                adapter = realEstateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }
        }

        viewModel.uiModelsLiveData.observe(viewLifecycleOwner) { realEstateAdapter.submitList(it) }

    }

    override fun onRealEstateClick(id: Int) {
        viewModel.onRealEstateSelected(id)
        realEstateClickListener.onRealEstateClick(id)
    }

}