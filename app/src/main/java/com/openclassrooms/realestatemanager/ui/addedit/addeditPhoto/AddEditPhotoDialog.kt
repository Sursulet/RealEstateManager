package com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.DialogAddEditPhotoBinding
import com.openclassrooms.realestatemanager.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.io.InputStream

@AndroidEntryPoint
class AddEditPhotoDialog : DialogFragment(R.layout.dialog_add_edit_photo) {

    lateinit var binding: DialogAddEditPhotoBinding
    private val viewModel: AddEditPhotoViewModel by viewModels()
    lateinit var image: Bitmap

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            val imageStream: InputStream? =
                uri?.let { requireContext().contentResolver.openInputStream(it) }
            imageStream?.let {
                val bitmap: Bitmap = BitmapFactory.decodeStream(it)
                displayImage(bitmap)
            }
        }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val bitmap: Bitmap = data?.extras?.get("data") as Bitmap
                displayImage(bitmap)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogAddEditPhotoBinding.bind(view)

        binding.apply {
            photoPreview.setImageBitmap(viewModel.photo)
            titlePhotoPreview.setText(viewModel.title)

            titlePhotoPreview.addTextChangedListener {
                titlePhotoPreviewLayout.error = null
                viewModel.title = it.toString()
            }

            actionOpenGallery.setOnClickListener { getContent.launch("image/*") }
            actionOpenCamera.setOnClickListener {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (intent.resolveActivity(requireContext().packageManager) != null) {
                    startForResult.launch(intent)
                }
            }
            actionCancel.setOnClickListener { dismiss() }
            actionOk.setOnClickListener {
                Log.d("PEACH", "onViewCreated: Click on OK")
                //val title: String
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                when (state) {
                    is AddEditPhotoViewModel.AddEditPhotoUiState.ShowInvalidInputMessage -> {
                        when (state.msg) {
                            AddEditPhotoViewModel.ErrorMessage.TITLE -> {
                                binding.titlePhotoPreviewLayout.error = "Title cannot be empty"
                            }
                            AddEditPhotoViewModel.ErrorMessage.PHOTO -> {
                                Snackbar.make(
                                    requireView(),
                                    "Photo cannot be empty",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    is AddEditPhotoViewModel.AddEditPhotoUiState.NavigateBackResult -> {
                        Log.d(TAG, "onViewCreated: OK OK")
                        //listener.onSavePhotoClick(state.result)
                        dismiss()
                    }
                    else -> Unit
                }
            }.exhaustive
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun displayImage(bitmap: Bitmap) {
        image = bitmap
        viewModel.photo = bitmap
        binding.photoPreview.setImageBitmap(bitmap)
    }

    companion object {
        const val TAG = "AddEditPhotoDialog"
    }
}