package com.openclassrooms.realestatemanager.ui.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstatesBinding
import com.openclassrooms.realestatemanager.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RealEstatesFragment : Fragment(), RealEstatesAdapter.OnItemClickListener {

    private var _binding: FragmentRealEstatesBinding? = null
    private val binding get() = _binding

    private val viewModel: RealEstatesViewModel by viewModels()
    private lateinit var realEstateAdapter: RealEstatesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding =  FragmentRealEstatesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realEstateAdapter = RealEstatesAdapter(this)

        binding?.apply {
            recyclerviewRealEstates.apply {
                adapter = realEstateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }
        }

        viewModel.uiModelsLiveData.observe(viewLifecycleOwner) {
            realEstateAdapter.submitList(it)
        }



        /*
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.realEstatesEvent.collect { event ->
                when(event) {
                    is RealEstatesViewModel.RealEstatesEvent.Show -> {
                        Snackbar.make(requireView(), "Real Estate deleted", Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.onUndoDeleteClick(event.realEstate)
                            }
                    }
                }
            }
        }

         */

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onIemClick(position: Int) {
        val id = realEstateAdapter.currentList[position].id
        //viewModel.onRealEstateSelected(position)
        //Toast.makeText(requireContext(), "$position / $id", Toast.LENGTH_SHORT).show()
        viewModel.setRealEstateIdQuery(id)
        val intent = Intent(requireContext(), DetailActivity::class.java)
        startActivity(intent)
        //viewModel.deleteAllRealEstates()
    }

}