package com.openclassrooms.realestatemanager.ui.detail

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

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
        }

        childFragmentManager.commit {
            val fragment = SupportMapFragment()

            fragment.getMapAsync { googleMap ->
                map = googleMap
                //Log.d("PEACH", "onViewCreated: " + Geocoder.isPresent())
            }

            replace(R.id.detail_map, fragment, null)
        }

        viewModel.uiModelLiveData.observe(viewLifecycleOwner) {
            binding.root.isVisible = true

            binding.apply {
                photosAdapter.submitList(it.photos)

                detailDescription.text = it.description
                detailBathrooms.text = it.bathrooms.toString()
                detailBedrooms.text = it.bedrooms.toString()
                detailLocation.text = it.address
                detailRooms.text = it.rooms.toString()
                detailSurface.text = it.surface.toString()

                it.coordinates?.let { xy ->
                    map?.addMarker(MarkerOptions().position(xy).title("Marker in Sydney"))
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(xy, 15f))
                }
            }
        }

    }


    private fun geoLocate() {
        Log.d("PEACH", "geoLocate: Bienvenue")
        val coder = Geocoder(requireContext(), Locale.getDefault())
        Log.d("PEACH", "geoLocate: CODER $coder")
        val addresses: List<Address>

        try {
            Log.d("PEACH", "geoLocate: HERE FFFFFFF")
            //Log.d("PEACH", "geoLocate: CODER ${coder.getFromLocationName("Dalvik, Iceland", 2)}")
            addresses = coder.getFromLocationName("1600 Amphitheatre Parkway, Mountain View, CA", 1)
            if (addresses.isNotEmpty()) {
                Log.d("PEACH", "geoLocate: YOU ${addresses.size}")
                //if (addresses == null) return
                val address: Address = addresses[0]
                val lat: Double = address.latitude
                val lng: Double = address.longitude
                Log.d("PEACH", "geoLocate: $lat $lng")
                val latlng = LatLng(lat, lng)
                map?.addMarker(MarkerOptions().position(latlng).title("Marker in Sydney"))
                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15f))
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("PEACH", "Could not get address..!")
        }

        /*
        try {
            val addressList: List<Address> = geoCoder.getFromLocationName("paris", 1)
            Log.d("PEACH", "onViewCreated: ${addressList.size}")
            if (addressList.isNotEmpty()) {
                Log.d("PEACH", "onViewCreated: BIENVENUE")
                val address: Address = addressList[0]
                Log.d("PEACH", "onViewCreated: $address")

                val point = LatLng(address.latitude, address.longitude)
                map?.addMarker(MarkerOptions().position(point).title("HERE"))
                map?.moveCamera(CameraUpdateFactory.newLatLng(point))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

         */
    }

}