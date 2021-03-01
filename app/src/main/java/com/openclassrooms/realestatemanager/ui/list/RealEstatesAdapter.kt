package com.openclassrooms.realestatemanager.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.openclassrooms.realestatemanager.databinding.ItemRealEstateBinding

class RealEstatesAdapter:ListAdapter<RealEstateUiModel,RealEstatesAdapter.RealEstatesViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RealEstatesViewHolder {
        val binding = ItemRealEstateBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return RealEstatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RealEstatesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class RealEstatesViewHolder(
        private val binding:ItemRealEstateBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel:RealEstateUiModel) {
            binding.apply {
                type.text = uiModel.type
                city.text= uiModel.city
                price.text= uiModel.price

                Glide.with(binding.img)
                    .load(uiModel.url)
                    .transform(CenterCrop())
                    .into(binding.img)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<RealEstateUiModel>() {
        override fun areItemsTheSame(
            oldItem: RealEstateUiModel,
            newItem: RealEstateUiModel
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: RealEstateUiModel,
            newItem: RealEstateUiModel
        ) = oldItem == newItem

    }
}