package com.openclassrooms.realestatemanager.ui.edit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentEditBinding
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import com.openclassrooms.realestatemanager.ui.detail.PhotosAdapter
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit), OnSaveClickListener {

    companion object {
        private const val EXTRA_IS_EDITING = "EXTRA_IS_EDITING"

        fun newInstance(isEditing: Boolean): EditFragment {
            val args = Bundle()
            args.putBoolean(EXTRA_IS_EDITING, isEditing)

            val fragment = EditFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var saveClickListener: OnSaveClickListener
    lateinit var binding: FragmentEditBinding

    private val viewModel: EditViewModel by viewModels()
    private lateinit var photosAdapter: PhotosAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        saveClickListener = (context as? OnSaveClickListener)
            ?: throw IllegalStateException("Activity should implement OnSaveClickListener")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            // Handle the returned Uri
        }

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                //Toast.makeText(requireContext(),"permission granted",Toast.LENGTH_SHORT).show()
                Snackbar.make(requireView(), "permission granted", Snackbar.LENGTH_LONG)
                    .setAction("OK") {
                        startCamera()
                    }.show()
            } else {
                Snackbar.make(requireView(), "permission denied", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK") { }
                    .show()
            }
        }

    private val requestGalleryPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                //Toast.makeText(requireContext(),"permission granted",Toast.LENGTH_SHORT).show()
                Snackbar.make(requireView(), "permission granted", Snackbar.LENGTH_LONG)
                    .setAction("OK") {
                        openGallery()
                    }.show()
            } else {
                Snackbar.make(requireView(), "permission denied", Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK") { }
                    .show()
            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEditBinding.bind(view)

        photosAdapter = PhotosAdapter()

        binding.apply {
            editRecyclerview.apply {
                adapter = photosAdapter
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                //addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                setHasFixedSize(true)
            }

            //list photos
            editType.addTextChangedListener { viewModel.realEstateType = it.toString() }
            editAddress.addTextChangedListener { viewModel.realEstateAddress = it.toString() }
            editCity.addTextChangedListener { viewModel.realEstateCity = it.toString() }
            editState.addTextChangedListener { viewModel.realEstateState = it.toString() }
            editPrice.addTextChangedListener { viewModel.realEstatePrice = it.toString() }
            editDesc.addTextChangedListener { viewModel.realEstateDesc = it.toString() }
            editNearest.addTextChangedListener { viewModel.realEstateNearest = it.toString() }
            editSurface.addTextChangedListener { viewModel.realEstateSurface = it.toString() }
            editRooms.addTextChangedListener { viewModel.realEstateRooms = it.toString() }
            editBedrooms.addTextChangedListener { viewModel.realEstateBedrooms = it.toString() }
            editBathrooms.addTextChangedListener { viewModel.realEstateBathrooms = it.toString() }
            editStatus.setOnCheckedChangeListener { _, isChecked ->
                viewModel.realEstateStatus = isChecked
            }
            editAgent.addTextChangedListener { viewModel.realEstateAgent = it.toString() }

            actionOpenCamera.setOnClickListener { capturePhoto("") }
            actionOpenGallery.setOnClickListener { openGallery() }
            actionSave.setOnClickListener { viewModel.onSaveClick() }
        }

        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                photosAdapter.submitList(it.photos)
                editType.setText(it.type)
                editAddress.setText(it.address)
                editCity.setText(it.city)
                editState.setText(it.state)
                editPrice.setText(it.price)
                editDesc.setText(it.description)
                editSurface.setText(it.surface)
                editRooms.setText(it.rooms)
                editBedrooms.setText(it.bedrooms)
                editBathrooms.setText(it.bathrooms)
                //editCreatedDate.isVisible = viewModel.isEdited != NO_REAL_ESTATE_ID
                //editCreatedDate.text = "${getTodayDate()}" //Besoin de la date de création non de la date du jour
                editStatus.isChecked = it.status
                editAgent.setText(it.agent)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditEvent.collect { event ->
                when (event) {
                    is EditViewModel.AddEditRealEstateEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                    is EditViewModel.AddEditRealEstateEvent.NavigateBackResult -> {
                        //binding.editType.clearFocus()
                        Snackbar.make(requireView(), "Back Result", Snackbar.LENGTH_LONG).show()
                        saveClickListener.onSaveClick()
                    }
                }.exhaustive
            }
        }

    }

    private fun openGallery() {
        getContent.launch("image/*")
    }

    private val cameraLaunch = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val photo = data?.extras?.get("data") as Bitmap
            binding.viewFinder.setImageBitmap(photo)
            val uiPhoto = PhotoUiModel(id=Math.random().toString(), url = "photo", name = "Essaie")
            viewModel.addPhoto(uiPhoto)
        }
    }

    private fun capturePhoto(targetFilename: String) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            //putExtra(MediaStore.EXTRA_OUTPUT, Uri.withAppendedPath(locationForPhotos, targetFilename))
        }

            cameraLaunch.launch(intent)
            //startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)

    }

    private fun startCamera() {}

    private fun hasReadExternalStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasWriteExternalStoragePermission() = ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

    private fun hasPermission(permission: String) = ContextCompat.checkSelfPermission(
        requireContext(),
        permission
    ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermission(permission: String) {
        when {
            hasPermission(permission) -> {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale(permission) -> showDialog(permission)
            else -> {
                if(permission == "Manifest.permission.CAMERA") {
                    requestCameraPermissionLauncher.launch(permission)
                }
                if(permission == "Manifest.permission.READ_EXTERNAL_STORAGE") {
                    requestGalleryPermissionLauncher.launch(permission)
                }
            }
        }
    }

    private fun showDialog(permission: String) {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setMessage("Permission to access is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { _, _ ->
                requestCameraPermissionLauncher.launch(permission)
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    override fun onSaveClick() {
        Log.d("PEACH", "onSaveClick: FRAG")
    }
}