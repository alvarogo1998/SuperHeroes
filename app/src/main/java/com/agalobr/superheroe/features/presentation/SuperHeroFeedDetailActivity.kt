package com.agalobr.superheroe.features.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.extensions.GsonSerialization
import com.agalobr.superheroe.app.extensions.errorDatabase
import com.agalobr.superheroe.app.extensions.errorInternet
import com.agalobr.superheroe.app.extensions.errorUnknown
import com.agalobr.superheroe.app.extensions.loadUrl
import com.agalobr.superheroe.databinding.ActivitySuperheroFeedDetailBinding
import com.agalobr.superheroe.features.data.ApiClient
import com.agalobr.superheroe.features.data.biography.BiographyDataRepository
import com.agalobr.superheroe.features.data.biography.local.XmlBiographyLocalDataSource
import com.agalobr.superheroe.features.data.biography.remote.api.BiographyDataRemoteSource
import com.agalobr.superheroe.features.data.connections.ConnectionsDataRepository
import com.agalobr.superheroe.features.data.connections.local.XmlConnectionsLocalDataSource
import com.agalobr.superheroe.features.data.connections.remote.api.ConnectionsDataRemoteSource
import com.agalobr.superheroe.features.data.powerstats.PowerstatsDataRepository
import com.agalobr.superheroe.features.data.powerstats.local.XmlPowerstatsLocalDataSource
import com.agalobr.superheroe.features.data.powerstats.remote.api.PowerstatsDataRemoteSource
import com.agalobr.superheroe.features.data.superheroe.SuperHeroeDataRepository
import com.agalobr.superheroe.features.data.superheroe.local.XmlSuperHeroeLocalDataSource
import com.agalobr.superheroe.features.data.superheroe.remote.api.SuperHeroesDataRemoteSource
import com.agalobr.superheroe.features.domain.SuperHeroFeedDetailUseCase
import com.agalobr.superheroe.features.presentation.adapter.SuperHeroeFeedDetailAdapter
import com.faltenreich.skeletonlayout.Skeleton


class SuperHeroFeedDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuperheroFeedDetailBinding
    private val superHeroDetailAdapter = SuperHeroeFeedDetailAdapter()
    private val skeleton: Skeleton? = null

    private val viewModel: SuperHeroFeedDetailViewModel by lazy {
        SuperHeroFeedDetailViewModel(
            SuperHeroFeedDetailUseCase(
                SuperHeroeDataRepository(
                    SuperHeroesDataRemoteSource(ApiClient()),
                    XmlSuperHeroeLocalDataSource(
                        this@SuperHeroFeedDetailActivity,
                        GsonSerialization()
                    )
                ),
                BiographyDataRepository(
                    BiographyDataRemoteSource(ApiClient()),
                    XmlBiographyLocalDataSource(
                        this@SuperHeroFeedDetailActivity,
                        GsonSerialization()
                    )
                ),
                ConnectionsDataRepository(
                    XmlConnectionsLocalDataSource(
                        this@SuperHeroFeedDetailActivity,
                        GsonSerialization()
                    ),
                    ConnectionsDataRemoteSource(ApiClient())
                ),
                PowerstatsDataRepository(
                    XmlPowerstatsLocalDataSource(
                        this@SuperHeroFeedDetailActivity,
                        GsonSerialization()
                    ),
                    PowerstatsDataRemoteSource(ApiClient())
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperheroFeedDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        setUpObserver()
        val id = intent.extras!!.getInt("id")
        viewModel.loadSuperHeroDetail(id)
    }

    private fun setUpObserver() {
        val observer =
            Observer<SuperHeroFeedDetailViewModel.DetailUiState> { uiStateSuperHeroeDetail ->

                if (uiStateSuperHeroeDetail.isLoading) {
                    skeleton?.showSkeleton()

                } else {
                    skeleton?.showOriginal()
                    if (uiStateSuperHeroeDetail.errorApp != null) {
                        showError(uiStateSuperHeroeDetail.errorApp)
                    } else {
                        skeleton?.showOriginal()
                        uiStateSuperHeroeDetail.superHero?.let {
                            bindData(it)
                        }
                    }
                }
            }
        viewModel.uiState.observe(this, observer)
    }

    private fun bindView() {
        binding = ActivitySuperheroFeedDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun bindData(heroDetail: SuperHeroFeedDetailUseCase.SuperHeroeDetail?) {
        binding.apply {
            imageSuperheroe.loadUrl(heroDetail!!.imageMain)
            textName.text = heroDetail.name
            textFullName.text = heroDetail.fullName
            textAlignment.text = heroDetail.alignment
            textGroupAffiliation.text = heroDetail.groupAffiliation
            textIntelligenceValue.text = heroDetail.intelligence.toString()
            textCombatValue.text = heroDetail.combat.toString()
            textSpeedValue.text = heroDetail.speed.toString()
            listImages.adapter = superHeroDetailAdapter
            listImages.layoutManager =
                LinearLayoutManager(
                    this@SuperHeroFeedDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

            superHeroDetailAdapter.setDataItems(heroDetail.images)
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