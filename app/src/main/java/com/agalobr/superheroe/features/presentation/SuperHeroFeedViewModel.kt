package com.agalobr.superheroe.features.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.agalobr.superheroe.app.error.ErrorApp
import com.agalobr.superheroe.features.domain.SuperHeroesFeedUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuperHeroFeedViewModel(private val superheroFeedUseCase: SuperHeroesFeedUseCase) :
    ViewModel() {

    private val _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> = _uiState

    fun loadSuperHero() {
        _uiState.postValue(
            UiState(isLoading = true)
        )
        viewModelScope.launch(Dispatchers.IO) {
            delay(5000)
            superheroFeedUseCase.invoke().fold(
                { responseError(it) },
                { responseLoadSuperHeroSuccess() }
            )
        }
    }

    private fun responseError(errorApp: ErrorApp) {
        _uiState.postValue(UiState(errorApp = errorApp))
    }

    private suspend fun responseLoadSuperHeroSuccess() {
        _uiState.postValue(
            UiState(
                superHero = superheroFeedUseCase.invoke().get()
            )
        )
    }

    data class UiState(
        val errorApp: ErrorApp? = null,
        val isLoading: Boolean = false,
        val superHero: List<SuperHeroesFeedUseCase.SuperHeroeList> = emptyList()
    )
}