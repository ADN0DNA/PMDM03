package com.example.myfragments.ui.scps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.data.RepositorioSites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScpsViewModel @Inject constructor(
    private val repositorioSites: RepositorioSites
) : ViewModel() {

    private val _uiState = MutableLiveData(ScpsState())
    val uiState: LiveData<ScpsState> get() = _uiState

    init {
        loadScps()
    }

    fun loadScps() {
        viewModelScope.launch {
            val scpsWithSites = repositorioSites.getScpsWithSites()
            _uiState.value = _uiState.value?.copy(scps = scpsWithSites)
        }
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}
