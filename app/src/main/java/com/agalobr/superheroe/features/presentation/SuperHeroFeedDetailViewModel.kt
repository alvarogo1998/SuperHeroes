package com.agalobr.superheroe.features.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.SuperHeroFeedDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SuperHeroFeedDetailViewModel(private val superHeroFeedDetailUseCase: SuperHeroFeedDetailUseCase) :
    ViewModel() {

    private val _uiState = MutableLiveData<DetailUiState>()
    val uiState: LiveData<DetailUiState> = _uiState

    fun loadSuperHeroDetail(heroId: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            superHeroFeedDetailUseCase.invoke(heroId).fold(
                { responseError(it) },
                { responseLoadSuperHeroSuccess(it) }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp) {
        _uiState.postValue(DetailUiState(errorApp = errorApp))
    }

    private suspend fun responseLoadSuperHeroSuccess(superHeroDetail: SuperHeroFeedDetailUseCase.SuperHeroeDetail) {
        _uiState.postValue(
            DetailUiState(
                superHero = superHeroDetail
            )
        )
    }

    data class DetailUiState(
        val errorApp: ErrorApp? = null,
        val isLoading : Boolean = false,
        val superHero: SuperHeroFeedDetailUseCase.SuperHeroeDetail? = null
    )
}