package com.openclassrooms.realestatemanager.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.map.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModels()

    private lateinit var map : GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync { googleMap ->

            map = googleMap
            /**
             * Manipulates the map once available.
             * This callback is triggered when the map is ready to be used.
             * This is where we can add markers or lines, add listeners or move the camera.
             * In this case, we just add a marker near Sydney, Australia.
             * If Google Play services is not installed on the device, the user will be prompted to
             * install it inside the SupportMapFragment. This method will only be triggered once the
             * user has installed Google Play services and returned to the app.
             */
            //val sydney = LatLng(-34.0, 151.0)
            //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

            with(map.uiSettings) {
                isZoomControlsEnabled = true
                isZoomGesturesEnabled = true
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state ->
                when(state) {
                    MapsViewModel.MapsUiState.Empty -> {}
                    is MapsViewModel.MapsUiState.Available -> {
                        map.addMarker(MarkerOptions().position(state.mapsUiModel.lastLocation).title("Marker in Sydney"))
                        //map.moveCamera(CameraUpdateFactory.newLatLng(state.mapsUiModel.lastLocation))
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(state.mapsUiModel.lastLocation, 10F))

                        state.mapsUiModel.markers.map {
                            map.addMarker(MarkerOptions().position(it))
                            //map.moveCamera(CameraUpdateFactory.newLatLng(it))
                        }
                    }
                    is MapsViewModel.MapsUiState.ShowErrorMessage -> TODO()
                }
            }
        }
    }
}