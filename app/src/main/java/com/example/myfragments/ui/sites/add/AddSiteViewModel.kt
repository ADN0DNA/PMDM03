package com.example.myfragments.ui.sites.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.domain.modelo.Site
import com.example.myfragments.domain.usecases.sites.AddSiteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddSiteViewModel @Inject constructor(
    private val addSiteUseCase: AddSiteUseCase
) : ViewModel() {
    private val _state = MutableLiveData(AddSiteState())
    val state: LiveData<AddSiteState> get() = _state

    fun guardar(site: Site) {
        viewModelScope.launch {
            val added = try {
                addSiteUseCase(site)
            } catch (t: Throwable) {
                null
            }

            if (added == null || added.id == 0) {
                _state.value = _state.value?.copy(
                    code = site.code,
                    location = site.location,
                    securityLevel = site.securityLevel,
                    director = site.director,
                    mensaje = "Error al guardar Site ${site.code}"
                )
            } else {
                _state.value = _state.value?.copy(
                    code = added.code,
                    location = added.location,
                    securityLevel = added.securityLevel,
                    director = added.director,
                    mensaje = "Site-${added.code} guardado"
                )
            }
        }
    }

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}

