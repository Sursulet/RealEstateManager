package com.openclassrooms.realestatemanager.ui.addedit

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentEditBinding
import com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto.AddEditPhotoDialog
import com.openclassrooms.realestatemanager.ui.detail.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_edit) {

    lateinit var binding: FragmentEditBinding

    private val viewModel: AddEditViewModel by viewModels()
    private lateinit var photosAdapter: PhotosAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditBinding.bind(view)
        photosAdapter = PhotosAdapter()

        binding.editRecyclerview.apply {
            adapter = photosAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {

            viewModel.uiState.collect { uiState ->
                when(uiState) {
                    AddEditViewModel.AddEditUiState.Empty -> {}
                    is AddEditViewModel.AddEditUiState.Content -> {
                        binding.apply {
                            photosAdapter.submitList(uiState.uiModel.photos)
                            editType.setText(uiState.uiModel.type)
                            editPrice.setText(uiState.uiModel.price)
                            editStreet.setText(uiState.uiModel.street)
                            editExtras.setText(uiState.uiModel.extras)
                            editCity.setText(uiState.uiModel.city)
                            editCode.setText(uiState.uiModel.code)
                            editCountry.setText(uiState.uiModel.country)
                            editPrice.setText(uiState.uiModel.price)
                            editDesc.setText(uiState.uiModel.description)
                            editSurface.setText(uiState.uiModel.surface)
                            editRooms.setText(uiState.uiModel.rooms)
                            editBedrooms.setText(uiState.uiModel.bedrooms)
                            editBathrooms.setText(uiState.uiModel.bathrooms)
                            editAgent.setText(uiState.uiModel.agent)
                        }
                    }

                    is AddEditViewModel.AddEditUiState.ShowInvalidInputMessage -> {
                        if (uiState.photosError != null) {
                            Snackbar.make(requireView(), uiState.photosError, Snackbar.LENGTH_SHORT).show()
                        }
                        binding.apply{
                            editLayoutType.error = uiState.typeError
                            editLayoutStreet.error = uiState.streetError
                            editLayoutExtras.error = uiState.extrasError
                            editLayoutCity.error = uiState.cityError
                            editLayoutCode.error = uiState.codeError
                            editLayoutCountry.error = uiState.countryError
                            editLayoutPrice.error = uiState.priceError
                            editLayoutSurface.error = uiState.surfaceError
                            editLayoutAgent.error = uiState.agentError
                        }
                    }

                    is AddEditViewModel.AddEditUiState.Success -> {
                        Snackbar.make(requireView(), uiState.msg, Snackbar.LENGTH_SHORT).show()
                        activity?.onBackPressed()
                    }
                }
            }
        }

        binding.apply {
            editType.addTextChangedListener { viewModel.realEstateType = it.toString() }
            editPrice.addTextChangedListener { viewModel.realEstatePrice = it.toString() }
            editStreet.addTextChangedListener { viewModel.realEstateStreet = it.toString() }
            editExtras.addTextChangedListener { viewModel.realEstateExtras = it.toString() }
            editCity.addTextChangedListener { viewModel.realEstateCity = it.toString() }
            editCode.addTextChangedListener { viewModel.realEstateCode = it.toString() }
            editCountry.addTextChangedListener { viewModel.realEstateCountry = it.toString() }
            editDesc.addTextChangedListener { viewModel.realEstateDesc = it.toString() }
            editNearest.addTextChangedListener { viewModel.realEstateNearest = it.toString() }
            editSurface.addTextChangedListener { viewModel.realEstateSurface = it.toString() }
            editRooms.addTextChangedListener { viewModel.realEstateRooms = it.toString() }
            editBathrooms.addTextChangedListener { viewModel.realEstateBathrooms = it.toString() }
            editBedrooms.addTextChangedListener { viewModel.realEstateBedrooms = it.toString() }
            editAgent.addTextChangedListener { viewModel.realEstateAgent = it.toString() }

            onAddEditPhoto.setOnClickListener {
                val addEditPhotoDialog = AddEditPhotoDialog()
                addEditPhotoDialog.show(childFragmentManager,"AddEditPhotoDialog")
            }

            actionSave.setOnClickListener { viewModel.onSaveClick() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
    }
}