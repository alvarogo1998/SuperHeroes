package com.agalobr.superheroe.features.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agalobr.superheroe.databinding.ViewSuperheroFeedItemBinding
import com.agalobr.superheroe.features.domain.SuperHeroesFeedUseCase

class SuperHeroeFeedAdapter:
    RecyclerView.Adapter<SuperHeroFeedViewHolder>() {

    private val listSuperHeroes: MutableList<SuperHeroesFeedUseCase.SuperHeroeList> = mutableListOf ()

    private var onClickDetail: ((Int) -> Unit)? = null

    fun setOnClickDetail(onClickDetail: ((Int) -> Unit)){
        this.onClickDetail = onClickDetail
    }

    fun setDataItems(superHero: List<SuperHeroesFeedUseCase.SuperHeroeList>){
        listSuperHeroes.clear()
        listSuperHeroes.addAll(superHero)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperHeroFeedViewHolder {
        val binding = ViewSuperheroFeedItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SuperHeroFeedViewHolder(binding)
    }

    override fun getItemCount(): Int = listSuperHeroes.size

    override fun onBindViewHolder(holder: SuperHeroFeedViewHolder, position: Int) =
        holder.bind(listSuperHeroes[position],onClickDetail)
}