package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)

        binding.root.isVisible = false

        val photoAdapter = PhotoAdapter()

        binding.apply {
            detailMediaRecyclerview.apply {
                adapter = photoAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

        viewModel.uiModelLiveData.observe(viewLifecycleOwner) {
            binding.root.isVisible = true
            binding.apply {

                detailDescription.text = it.description
                detailBathrooms.text = it.bathrooms.toString()
                detailBedrooms.text = it.bedrooms.toString()
                detailLocation.text = it.address
                detailRooms.text = it.rooms.toString()
                detailSurface.text = it.surface.toString()
            }
        }


        childFragmentManager.commit {
            val fragment = SupportMapFragment()

            fragment.getMapAsync {
                val sydney = LatLng(-34.0, 151.0)
                it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            }

            add(R.id.detail_map,fragment,null)
        }

    }

}