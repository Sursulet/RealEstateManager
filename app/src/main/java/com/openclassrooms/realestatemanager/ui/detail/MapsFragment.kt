package com.openclassrooms.realestatemanager.ui.detail

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.ui.map.MapsViewModel
import com.openclassrooms.realestatemanager.utils.Constants
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MapsFragment : Fragment() {

    private val viewModel: MapsViewModel by viewModels()

    private lateinit var map : GoogleMap

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if(isGranted) {
                Log.d(TAG, "Permission: Granted")
            } else {
                Log.d(TAG, "Permission: Denied")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
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
                isCompassEnabled = false
                isZoomGesturesEnabled = true
                isScrollGesturesEnabled = true
            }

            map.setOnCameraMoveListener {
                viewModel.zoomLevel.value = map.cameraPosition.zoom
                //Log.d(TAG, "ZOOM: $level")
            }
        }

         */

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.uiState.collect { state ->
                when (state) {
                    MapsViewModel.MapsUiState.Empty -> {
                    }
                    is MapsViewModel.MapsUiState.Available -> {
                        Log.d(TAG, "onViewCreated: ${state.mapsUiModel.lastLocation}")
                        /*
                        map.addMarker(
                            MarkerOptions().position(state.mapsUiModel.lastLocation)
                                .title("Marker in Sydney")
                        )
                        //map.moveCamera(CameraUpdateFactory.newLatLng(state.mapsUiModel.lastLocation))
                        //map.animateCamera(CameraUpdateFactory.newLatLngZoom(state.mapsUiModel.lastLocation, 10F))
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                state.mapsUiModel.lastLocation,
                                10F
                            )
                        )

                        state.mapsUiModel.markers.map {
                            map.addMarker(MarkerOptions().position(it))
                            //map.moveCamera(CameraUpdateFactory.newLatLng(it))
                        }

                         */
                    }
                    is MapsViewModel.MapsUiState.ShowErrorMessage -> {
                        Log.d(TAG, "onViewCreated: ERROR: ${state.permission} || ${state.network}")
                        if (!state.network) {
                            Toast.makeText(
                                requireContext(),
                                "Need connexion state.msg",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        if (!state.permission) {
                            ActivityCompat.requestPermissions(
                                requireActivity(),
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                Constants.REQUEST_PERMISSIONS_REQUEST_CODE
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        //viewModel.check()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates()
    }
}