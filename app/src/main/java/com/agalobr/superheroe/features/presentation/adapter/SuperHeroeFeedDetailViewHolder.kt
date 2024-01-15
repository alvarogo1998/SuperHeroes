package com.agalobr.superheroe.features.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.agalobr.superheroe.app.extensions.loadUrl
import com.agalobr.superheroe.databinding.ViewImagesSuperheroDetailItemBinding

class SuperHeroeFeedDetailViewHolder(binding: ViewImagesSuperheroDetailItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        private val binding = ViewImagesSuperheroDetailItemBinding.bind(binding.root)

    fun bind(image: String) = binding.imageSuperheroeDetail.loadUrl(image)
}