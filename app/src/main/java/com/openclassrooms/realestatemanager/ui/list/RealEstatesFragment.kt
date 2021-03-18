package com.openclassrooms.realestatemanager.ui.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.databinding.FragmentRealEstatesBinding
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.lang.IllegalStateException

@AndroidEntryPoint
class RealEstatesFragment : Fragment(), OnRealEstateClickListener {

    private var _binding: FragmentRealEstatesBinding? = null
    private val binding get() = _binding

    private val viewModel: RealEstatesViewModel by viewModels()
    private lateinit var realEstateAdapter: RealEstatesAdapter

    private lateinit var realEstateClickListener : OnRealEstateClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        realEstateClickListener = (context as? OnRealEstateClickListener)
            ?: throw IllegalStateException("Activity should implement OnRealEstateClickListener")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding =  FragmentRealEstatesBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        realEstateAdapter = RealEstatesAdapter(this)

        binding?.apply {
            recyclerviewRealEstates.apply {
                adapter = realEstateAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }
        }

        setFragmentResultListener("edit_request") { _, bundle ->
            val result = bundle.getInt("edit_result")
            viewModel.onEditResult(result)
        }

        viewModel.uiModelsLiveData.observe(viewLifecycleOwner) {
            //Log.d("PEACH", "onViewCreated: " + it[0].url)
            realEstateAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.realEstatesEvent.collect { event ->
                when(event) {
                    is RealEstatesViewModel.RealEstatesEvent.NavigateToAddRealEstateScreen -> {}
                    is RealEstatesViewModel.RealEstatesEvent.NavigateToEditRealEstateScreen -> TODO()
                    is RealEstatesViewModel.RealEstatesEvent.ShowRealEstateSavedConfirmationMessage -> TODO()
                }
            }.exhaustive
        }

    }

    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                Toast.makeText(requireContext(), "ADD", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_edit -> {
                Toast.makeText(requireContext(), "EDIT", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_search -> {
                Toast.makeText(requireContext(), "SEARCH", Toast.LENGTH_SHORT).show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRealEstateClick(model: RealEstateUiModel) {
        viewModel.onRealEstateSelected(id)

        realEstateClickListener.onRealEstateClick(model)
    }

}