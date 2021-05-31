package com.openclassrooms.realestatemanager.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding
import com.openclassrooms.realestatemanager.ui.addedit.AddEditActivity
import dagger.hilt.android.AndroidEntryPoint

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

        viewModel.uiModelLiveData.observe(viewLifecycleOwner) {
            binding.root.isVisible = true

            binding.apply {
                photosAdapter.submitList(it?.photos)

                detailDescription.text = it?.description
                detailBathrooms.text = it?.bathrooms.toString()
                detailBedrooms.text = it?.bedrooms.toString()
                detailLocation.text = it?.address
                detailRooms.text = it?.rooms.toString()
                detailSurface.text = it?.surface.toString()

                it?.coordinates?.let { xy ->
                    map?.addMarker(MarkerOptions().position(xy).title(it.address))
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(xy, 15f))
                }
            }
        }

    }

}