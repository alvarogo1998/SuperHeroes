package com.agalobr.superheroe.features.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.agalobr.superheroe.R
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.app.extensions.GsonSerialization
import com.agalobr.superheroe.app.extensions.errorDatabase
import com.agalobr.superheroe.app.extensions.errorInternet
import com.agalobr.superheroe.app.extensions.errorUnknown
import com.agalobr.superheroe.databinding.FragmentSuperheroFeedBinding
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

class SuperHeroFragment : Fragment() {

    private var _binding: FragmentSuperheroFeedBinding? = null
    private val binding = _binding!!

    private val skeleton: Skeleton by lazy {
        binding.listSuperHeroe.applySkeleton(R.layout.view_superhero_feed_item, 10)
    }

    private val superHeroAdapter = SuperHeroeFeedAdapter()

    private val viewModel: SuperHeroFeedViewModel by lazy {
        SuperHeroFeedViewModel(
            SuperHeroesFeedUseCase(
                WorkDataRepository(
                    XmlWorkLocalDataSource(requireContext(), GsonSerialization()),
                    WorkDataRemoteSource(ApiClient())
                ),
                BiographyDataRepository(
                    BiographyDataRemoteSource(ApiClient()),
                    XmlBiographyLocalDataSource(requireContext(), GsonSerialization())
                ),
                SuperHeroeDataRepository(
                    SuperHeroesDataRemoteSource(ApiClient()),
                    XmlSuperHeroeLocalDataSource(requireContext(), GsonSerialization())
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuperheroFeedBinding.inflate(inflater, container, false)
        setUpView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.loadSuperHero()
    }

    private fun setupObservers() {
        val observer = Observer<SuperHeroFeedViewModel.UiState> { uiStateSuperHeroFeed ->

            if (uiStateSuperHeroFeed.isLoading) {
                skeleton.showSkeleton()
            } else {
                skeleton.showOriginal()
                if (uiStateSuperHeroFeed.errorApp != null) {
                    showError(uiStateSuperHeroFeed.errorApp)
                } else {
                    superHeroAdapter.setDataItems(uiStateSuperHeroFeed.superHero)
                    superHeroAdapter.setOnClickDetail {
                        navigateToDetail(it)
                    }
                }
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner, observer)
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

    private fun navigateToDetail(id: Int) {
        val result = id
        (activity as MainActivity).changeFragment(SuperHeroFragmentDetail.newInstance())
        setFragmentResult("requestKey", bundleOf("bundleKey" to result))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}