package com.openclassrooms.realestatemanager.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.openclassrooms.realestatemanager.databinding.ItemPhotoBinding

class PhotosAdapter : ListAdapter<PhotoUiModel, PhotosAdapter.PhotosViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class PhotosViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: PhotoUiModel) {
            binding.apply {
                name.text = uiModel.name
                Glide.with(img)
                    .load(uiModel.url)
                    .transform(CenterCrop())
                    .into(img)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PhotoUiModel>() {
        override fun areItemsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel) =
            oldItem == newItem
    }
}