package com.openclassrooms.realestatemanager.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentEditBinding
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val viewModel: EditViewModel by viewModels()

    /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditBinding.bind(view)

        binding.apply {
            editType.setText(viewModel.realEstateType)
            editCity.setText(viewModel.realEstateCity)
            editPrice.setText(viewModel.realEstatePrice)
            editDesc.setText(viewModel.realEstateDesc)

            editType.addTextChangedListener { viewModel.realEstateType = it.toString() }
            editCity.addTextChangedListener { viewModel.realEstateCity = it.toString() }
            editPrice.addTextChangedListener { viewModel.realEstatePrice = it.toString() }
            editDesc.addTextChangedListener { viewModel.realEstateDesc = it.toString() }

            actionSave.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.editRealEstateEvent.collect { event ->
                when (event) {
                    is EditViewModel.EditRealEstateEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is EditViewModel.EditRealEstateEvent.NavigateBackResult -> {
                        binding.editDesc.clearFocus()
                    }
                }.exhaustive
            }
        }
    }
}