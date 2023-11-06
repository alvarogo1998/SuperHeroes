package com.agalobr.superheroe.features.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agalobr.superheroe.databinding.ViewImagesSuperheroDetailItemBinding

class SuperHeroeFeedDetailAdapter : RecyclerView.Adapter<SuperHeroeFeedDetailViewHolder>() {

    private val dataItems = mutableListOf<String>()

    fun setDataItems(imageHero: List<String>){
        dataItems.addAll(imageHero)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroeFeedDetailViewHolder {
        val binding = ViewImagesSuperheroDetailItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SuperHeroeFeedDetailViewHolder(binding)
    }

    override fun getItemCount(): Int = dataItems.size

    override fun onBindViewHolder(holder: SuperHeroeFeedDetailViewHolder, position: Int) =
        holder.bind(dataItems[position])

}