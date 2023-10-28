package com.agalobr.superheroe.features.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agalobr.superheroe.R
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.extensions.GsonSerialization
import com.agalobr.superheroe.app.extensions.errorDatabase
import com.agalobr.superheroe.app.extensions.errorInternet
import com.agalobr.superheroe.app.extensions.errorUnknown
import com.agalobr.superheroe.databinding.ActivitySuperheroFeedBinding
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.biography.BiographyDataRepository
import com.agalobr.superheroe.features.data.biography.local.XmlBiographyLocalDataSource
import com.agalobr.superheroe.features.data.biography.remote.api.BiographyDataRemoteSource
import com.agalobr.superheroe.features.data.superheroe.SuperHeroeDataRepository
import com.agalobr.superheroe.features.data.superheroe.local.XmlSuperHeroeLocalDataSource
import com.agalobr.superheroe.features.data.superheroe.remote.api.SuperHeroesDataRemoteSource
import com.agalobr.superheroe.features.data.work.WorkDataRepository
import com.agalobr.superheroe.features.data.work.local.XmlWorkLocalDataSource
import com.agalobr.superheroe.features.data.work.remote.api.WorkDataRemoteSource
import com.agalobr.superheroe.features.domain.SuperHeroesFeedUseCase
import com.agalobr.superheroe.features.presentation.adapter.SuperHeroeFeedAdapter
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.applySkeleton

class SuperHeroFeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperheroFeedBinding

    private val skeleton: Skeleton by lazy {
        binding.listSuperHeroe.applySkeleton(R.layout.view_superhero_feed_item, 10)
    }

    private val superHeroAdapter = SuperHeroeFeedAdapter()

    private val viewModel: SuperHeroFeedViewModel by lazy {
        SuperHeroFeedViewModel(
            SuperHeroesFeedUseCase(
                WorkDataRepository(
                    XmlWorkLocalDataSource(this@SuperHeroFeedActivity, GsonSerialization()),
                    WorkDataRemoteSource(ApiClient())
                ),
                BiographyDataRepository(
                    BiographyDataRemoteSource(ApiClient()),
                    XmlBiographyLocalDataSource(this@SuperHeroFeedActivity, GsonSerialization())
                ),
                SuperHeroeDataRepository(
                    SuperHeroesDataRemoteSource(ApiClient()),
                    XmlSuperHeroeLocalDataSource(this@SuperHeroFeedActivity, GsonSerialization())
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpObserver()
        setUpView()
        viewModel.loadSuperHero()

    }

    private fun setUpObserver() {
        val observer = Observer<SuperHeroFeedViewModel.UiState> { uiStateSuperHeroFeed ->

            if (uiStateSuperHeroFeed.isLoading) {
                skeleton.showSkeleton()
            } else {
                skeleton.showOriginal()
                if (uiStateSuperHeroFeed.errorApp != null) {
                    showError(uiStateSuperHeroFeed.errorApp)
                } else {
                    superHeroAdapter.setDataItems(uiStateSuperHeroFeed.superHero)
                }
            }
        }
        viewModel.uiState.observe(this, observer)
    }

    private fun setUpView() {
        binding.apply {
            listSuperHeroe.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = superHeroAdapter
            }
        }
    }

    private fun showError(error: ErrorApp) {
        when (error) {
            ErrorApp.DatabaseErrorApp -> errorDatabase()
            ErrorApp.InternetErrorApp -> errorInternet()
            ErrorApp.UnKnownError -> errorUnknown()
        }
    }

    private fun errorUnknown() = binding.viewError.errorUnknown()

    private fun errorDatabase() = binding.viewError.errorDatabase()

    private fun errorInternet() = binding.viewError.errorInternet()
}