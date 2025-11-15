package com.example.myfragments.ui.sites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.data.RepositorioSites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SitesViewModel @Inject constructor(
    private val repositorioSites: RepositorioSites
) : ViewModel() {

    private val _uiState = MutableLiveData(SitesState())
    val uiState: LiveData<SitesState> get() = _uiState

    init {
        loadSites()
    }

    fun loadSites() {
        viewModelScope.launch {
            val sites = repositorioSites.getAllSites()
            _uiState.value = _uiState.value?.copy(sites = sites)
        }
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}
