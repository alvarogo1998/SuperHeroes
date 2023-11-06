package com.agalobr.superheroe.features.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.agalobr.superheroe.app.extensions.loadUrl
import com.agalobr.superheroe.databinding.ViewSuperheroFeedItemBinding
import com.agalobr.superheroe.features.domain.SuperHeroesFeedUseCase

class SuperHeroFeedViewHolder(binding: ViewSuperheroFeedItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private val binding = ViewSuperheroFeedItemBinding.bind(binding.root)

    fun bind(superHero: SuperHeroesFeedUseCase.SuperHeroeList, onClickDetail: ((Int) -> Unit)?) {

        binding.apply {
            imageSuperheroe.loadUrl(superHero.image)
            textName.text = superHero.name
            textFullName.text = superHero.fullName
            textOccupation.text = superHero.occupation
            icArrow.setOnClickListener {
                onClickDetail!!.invoke(superHero.id)
            }
        }
    }
}