package com.openclassrooms.realestatemanager.ui.edit

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
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

    private val PERMISSION_READ_KEY = 1001
    private val PERMISSION_WRITE_KEY = 1002
    private val CAMERA_KEY = 1002
    private val IMG_KEY = 110

    private val viewModel: EditViewModel by viewModels()

    private var photos = ArrayList<String>()

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
            //TODO xml buttons add photo from gallery and from camera
            editType.setText(viewModel.realEstateType)
            editAddress.setText(viewModel.realEstateAddress)
            editCity.setText(viewModel.realEstateCity)
            editPrice.setText(viewModel.realEstatePrice.toString())
            editDesc.setText(viewModel.realEstateDesc)
            editCreatedDate.isVisible = viewModel.realEstate != null
            //editCreatedDate.text = "${viewModel.realEstate?.createdDateFormatted}"
            //editStatus.text = viewModel.realEstateStatus
            editAgent.setText(viewModel.realEstateAgent)

            editType.addTextChangedListener { viewModel.realEstateType = it.toString() }
            editAddress.addTextChangedListener { viewModel.realEstateAddress = it.toString() }
            editCity.addTextChangedListener { viewModel.realEstateCity = it.toString() }
            editPrice.addTextChangedListener { viewModel.realEstatePrice = it.toString().toFloat() }
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
                        binding.editType.clearFocus()
                        binding.editAddress.clearFocus()
                        binding.editCity.clearFocus()
                        binding.editPrice.clearFocus()
                        binding.editDesc.clearFocus()

                        setFragmentResult("edit_request", bundleOf("edit_result" to event.result))
                        //TODO: back to the list
                    }
                }.exhaustive
            }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED) &&
                (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED)
            ) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                requestPermissions(permission, PERMISSION_READ_KEY)
                requestPermissions(permissionCoarse, PERMISSION_WRITE_KEY)
            } else {
                importImg()
            }
        }
    }

    private fun importImg() {
        val intent =
            Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "images/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "select a picture"), IMG_KEY)

    }

    fun importWithCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_KEY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMG_KEY) {
            if (requestCode == RESULT_OK) {
                if (data?.data != null) {
                    val selectedImg: Uri? = data.data
                    photos.add(selectedImg.toString())
                } else if (data!!.clipData != null) {
                    (0 until data.clipData!!.itemCount).forEach { i ->
                        val uri = data.clipData!!.getItemAt(i).uri
                        photos.add(uri.toString())
                    }
                }
            } else if (resultCode == CAMERA_KEY) {
                if (requestCode == RESULT_OK) {
                    if (data?.data != null) {
                        val selectedImg: Uri? = data.data
                        photos.add(selectedImg.toString())
                    }
                }
            }
        }
    }
}