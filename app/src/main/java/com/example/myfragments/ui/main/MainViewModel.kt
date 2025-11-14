package com.example.myfragments.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfragments.domain.usecases.scps.GetAllScpsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllScpsUseCase: GetAllScpsUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData(MainState())
    val uiState: LiveData<MainState> get() = _uiState

    init {
        loadScps()
    }

    fun loadScps() {
        _uiState.value = _uiState.value?.copy(scps = getAllScpsUseCase.invoke())
    }

    fun limpiarMensaje() {
        _uiState.value = _uiState.value?.copy(mensaje = null)
    }
}
