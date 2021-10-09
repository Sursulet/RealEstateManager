package com.openclassrooms.realestatemanager.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding
import com.openclassrooms.realestatemanager.ui.addedit.AddEditActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()
    private var map: GoogleMap? = null

    //TODO : onCreateView binding visibility

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)

        binding.root.isVisible = false

        val photosAdapter = PhotosAdapter()

        binding.apply {
            detailMediaRecyclerview.apply {
                adapter = photosAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }

            actionEdit?.setOnClickListener {
                val intent = Intent(requireContext(), AddEditActivity::class.java)
                startActivity(intent)
            }
        }

        childFragmentManager.commit {
            val fragment = SupportMapFragment()
            fragment.getMapAsync { googleMap -> map = googleMap }
            replace(R.id.detail_map, fragment, null)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { uiState ->
                binding.root.isVisible = true

                binding.apply {
                    photosAdapter.submitList(uiState?.photos)

                    detailDescription.text = uiState?.description
                    detailBathrooms.text = uiState?.bathrooms.toString()
                    detailBedrooms.text = uiState?.bedrooms.toString()
                    detailLocation.text = uiState?.address
                    detailRooms.text = uiState?.rooms.toString()
                    detailSurface.text = uiState?.surface.toString()

                    uiState?.coordinates?.let { xy ->
                        map?.addMarker(MarkerOptions().position(xy).title(uiState.address))
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(xy, 15f))
                    }
                }

            }
        }

    }

}