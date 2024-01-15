package com.agalobr.superheroe.features.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.extensions.GsonSerialization
import com.agalobr.superheroe.app.extensions.errorDatabase
import com.agalobr.superheroe.app.extensions.errorInternet
import com.agalobr.superheroe.app.extensions.errorUnknown
import com.agalobr.superheroe.app.extensions.loadUrl
import com.agalobr.superheroe.databinding.FragmentSuperheroFeedDetailBinding
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

class SuperHeroFragmentDetail : Fragment() {

    private var _binding: FragmentSuperheroFeedDetailBinding? = null
    private val binding get() = _binding!!

    private val superHeroDetailAdapter = SuperHeroeFeedDetailAdapter()

    private val viewModel: SuperHeroFeedDetailViewModel by lazy {
        SuperHeroFeedDetailViewModel(
            SuperHeroFeedDetailUseCase(
                SuperHeroeDataRepository(
                    SuperHeroesDataRemoteSource(ApiClient()),
                    XmlSuperHeroeLocalDataSource(
                        requireContext(),
                        GsonSerialization()
                    )
                ),
                BiographyDataRepository(
                    BiographyDataRemoteSource(ApiClient()),
                    XmlBiographyLocalDataSource(
                        requireContext(),
                        GsonSerialization()
                    )
                ),
                ConnectionsDataRepository(
                    XmlConnectionsLocalDataSource(
                        requireContext(),
                        GsonSerialization()
                    ),
                    ConnectionsDataRemoteSource(ApiClient())
                ),
                PowerstatsDataRepository(
                    XmlPowerstatsLocalDataSource(
                        requireContext(),
                        GsonSerialization()
                    ),
                    PowerstatsDataRemoteSource(ApiClient())
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperheroFeedDetailBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            val result = bundle.getInt("bundleKey")
            viewModel.loadSuperHeroDetail(result)
        }
    }

    private fun setupView() {
        binding.apply {
            listImages.apply {
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = superHeroDetailAdapter
            }
        }
    }

    private fun setUpObserver() {
        val observer =
            Observer<SuperHeroFeedDetailViewModel.DetailUiState> { uiStateSuperHeroeDetail ->

                if (uiStateSuperHeroeDetail.errorApp != null) {
                    showError(uiStateSuperHeroeDetail.errorApp)
                } else {
                    uiStateSuperHeroeDetail.superHero?.let {
                        bindData(it)
                    }
                }
            }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
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

    companion object {
        fun newInstance() = SuperHeroFragmentDetail()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}