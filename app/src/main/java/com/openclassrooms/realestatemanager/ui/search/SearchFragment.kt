package com.openclassrooms.realestatemanager.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchBinding
import com.openclassrooms.realestatemanager.ui.list.OnRealEstateClickListener
import com.openclassrooms.realestatemanager.ui.list.RealEstatesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), OnRealEstateClickListener {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var realEstateAdapter: RealEstatesAdapter

    private lateinit var realEstateClickListener: OnRealEstateClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)

        realEstateAdapter = RealEstatesAdapter(this)

        binding.apply {
            searchRecyclerview.apply {
                adapter = realEstateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }
        }


        /*
        viewModel.search.observe(viewLifecycleOwner) {
            it?.let { realEstateAdapter.submitList(it) }
        }*/
    }

    override fun onRealEstateClick(id: Int) {
        viewModel.onRealEstateSelected(id)
        realEstateClickListener.onRealEstateClick(id)
    }
}