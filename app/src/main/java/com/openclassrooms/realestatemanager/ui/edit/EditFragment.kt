package com.openclassrooms.realestatemanager.ui.edit

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val PERMISSION_READ_KEY = 1001
    private val PERMISSION_WRITE_KEY = 1002
    private val CAMERA_KEY = 1002
    private val IMG_KEY = 110

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

    /*
    @Inject
    lateinit var editViewModelFactory: EditViewModel.AssistedFactory

     */

    private val viewModel: EditViewModel by viewModels() /*{
        EditViewModel.provideFactory(
            editViewModelFactory,
            requireArguments().getBoolean(EXTRA_IS_EDITING)
        )
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentEditBinding.bind(view)

        binding.apply {
            editType.addTextChangedListener { viewModel.realEstateType = it.toString() }
            editAddress.addTextChangedListener { viewModel.realEstateAddress = it.toString() }
            editCity.addTextChangedListener { viewModel.realEstateCity = it.toString() }
            editState.addTextChangedListener { viewModel.realEstateState = it.toString() }
            editPrice.addTextChangedListener { viewModel.realEstatePrice = it.toString() }
            editDesc.addTextChangedListener { viewModel.realEstateDesc = it.toString() }

            actionPickImages.setOnClickListener { importImg() }
            actionSave.setOnClickListener { viewModel.onSaveClick() }
        }

        viewModel.uiLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                editType.setText(it.type)
                editAddress.setText(it.address)
                editCity.setText(it.city)
                editState.setText(it.state)
                editPrice.setText(it.price.toString())
                editDesc.setText(it.description)
                //editSurface.setText(it.surface)
                //editRooms.setText(it.rooms)
                //editBedrooms.setText(it.bedrooms)
                //editBathrooms.setText(it.bathrooms)
                //editCreatedDate.isVisible = viewModel.realEstate != null
                //editCreatedDate.text = "${viewModel.realEstate?.createdDateFormatted}"
                //editStatus.text = viewModel.realEstateStatus
                editAgent.setText(it.agent)
            }
        }

        /*
        viewModel.liveData.observe(viewLifecycleOwner) {
            Log.d("PEACH", "onViewCreated: $it")
            binding.apply {
                editType.setText(it.type)
                editAddress.setText(it.address)
                editCity.setText(it.city)
                editPrice.setText(it.price.toString())
                editDesc.setText(it.description)
                //editSurface.setText(it.surface)
                //editRooms.setText(it.rooms)
                //editBedrooms.setText(it.bedrooms)
                //editBathrooms.setText(it.bathrooms)
                //editCreatedDate.isVisible = viewModel.realEstate != null
                //editCreatedDate.text = "${viewModel.realEstate?.createdDateFormatted}"
                //editStatus.text = viewModel.realEstateStatus
                editAgent.setText(it.agent)
            }
        }

         */
    }

    private fun checkPermission(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_SHORT).show()
                }

                shouldShowRequestPermissionRationale(permission) -> showDialog(permission,name, requestCode)

                else -> ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        fun innerCheck(name: String) {
            if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "$name permission refused", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "$name permission granted", Toast.LENGTH_SHORT).show()
            }
        }

        when (requestCode) {
            IMG_KEY -> innerCheck("gallery")
            CAMERA_KEY -> innerCheck("camera")
        }
    }

    private fun showDialog(permission: String,name: String,requestCode: Int) {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle("Permission required")
            setPositiveButton("OK") { dialog, which ->
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
            }
        }

        val dialog = builder.create()
        dialog.show()
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
        }
    }

    private fun importImg() {
        val intent = Intent()
            //Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "images/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        //resultLauncher.launch(Intent.createChooser(intent, "select a picture"), IMG_KEY)

    }

    fun importWithCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //resultLauncher.launch(intent, CAMERA_KEY)
    }
}