package com.openclassrooms.realestatemanager.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.openclassrooms.realestatemanager.databinding.ItemPhotoBinding

class PhotoAdapter : ListAdapter<PhotoUiModel, PhotoAdapter.PhotoViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return PhotoAdapter.PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class PhotoViewHolder(
        private val binding:ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: PhotoUiModel) {
            binding.apply {
                name.text = uiModel.name
                Glide.with(binding.img)
                    .load(uiModel.url)
                    .transform(CenterCrop())
                    .into(binding.img)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PhotoUiModel>() {
        override fun areItemsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel): Boolean
                = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: PhotoUiModel, newItem: PhotoUiModel): Boolean
                = oldItem == newItem
    }
}