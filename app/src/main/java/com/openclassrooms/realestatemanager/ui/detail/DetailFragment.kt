package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.fragment.app.findFragment
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailBinding.bind(view)
        val photoAdapter = PhotoAdapter()

        binding.apply {
            detailMediaRecyclerview.apply {
                adapter = photoAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
            }
        }

        viewModel.uiModelLiveData.observe(viewLifecycleOwner) {
            binding.apply {

                detailDescription.text = it.description
                detailBathrooms.text = it.bathrooms.toString()
                detailBedrooms.text = it.bedrooms.toString()
                detailLocation.text = it.address
                detailRooms.text = it.rooms.toString()
                detailSurface.text = it.surface.toString()

                //photoAdapter.submitList()
            }
        }

        val mapFragmentContainer = binding.detailMap // childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        childFragmentManager.commit {
            val fragment = SupportMapFragment()

            fragment.getMapAsync {
                val sydney = LatLng(-34.0, 151.0)
                it.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                it.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                //viewModel.mapIsReady()
            }

            // TODO A Vérifier
            add(mapFragmentContainer.id, fragment, null)
        }
    }

}