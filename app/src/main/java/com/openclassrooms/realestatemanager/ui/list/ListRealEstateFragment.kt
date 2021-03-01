package com.openclassrooms.realestatemanager.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.databinding.FragmentListRealEstateBinding
import com.openclassrooms.realestatemanager.ui.viewmodels.ListRealEstateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListRealEstateFragment : Fragment() {

    private val viewModel: ListRealEstateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentListRealEstateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding =  FragmentListRealEstateBinding.bind(view)
        val realEstateAdapter = RealEstatesAdapter()

        binding.apply {
            recyclerview.apply {
                adapter = realEstateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.uiModelsLiveData.observe(viewLifecycleOwner) {
            realEstateAdapter.submitList(it)
        }
    }

}