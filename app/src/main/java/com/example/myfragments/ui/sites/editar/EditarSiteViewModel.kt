package com.example.myfragments.ui.sites.editar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.domain.modelo.Site
import com.example.myfragments.domain.usecases.sites.DeleteSiteUseCase
import com.example.myfragments.domain.usecases.sites.GetSiteUseCase
import com.example.myfragments.domain.usecases.sites.UpdateSiteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditarSiteViewModel @Inject constructor(
    private val getSiteUseCase: GetSiteUseCase,
    private val updateSiteUseCase: UpdateSiteUseCase,
    private val deleteSiteUseCase: DeleteSiteUseCase
) : ViewModel() {

    private val _state = MutableLiveData(EditarSiteState())
    val state: LiveData<EditarSiteState> get() = _state

    private var currentId: Int = 0

    fun cargar(id: Int) {
        currentId = id
        viewModelScope.launch {
            val site = try {
                getSiteUseCase(id)
            } catch (t: Throwable) {
                null
            }

            if (site != null) {
                _state.value = _state.value?.copy(site = site, cerrar = false)
            } else {
                _state.value = _state.value?.copy(
                    mensaje = "Site no encontrado",
                    cerrar = true
                )
            }
        }
    }

    fun guardar(site: Site) {
        viewModelScope.launch {
            val ok = try {
                updateSiteUseCase(currentId, site)
            } catch (t: Throwable) {
                false
            }

            if (ok) {
                _state.value = _state.value?.copy(
                    mensaje = "Site-${site.code} actualizado",
                    cerrar = true
                )
            } else {
                _state.value = _state.value?.copy(mensaje = "Error al actualizar")
            }
        }
    }

    fun borrar() {
        if (currentId == 0) {
            _state.value = _state.value?.copy(mensaje = "No hay Site seleccionado")
            return
        }

        viewModelScope.launch {
            val ok = try {
                deleteSiteUseCase(currentId)
            } catch (t: Throwable) {
                false
            }

            if (ok) {
                _state.value = _state.value?.copy(
                    mensaje = "Site borrado",
                    cerrar = true
                )
            } else {
                _state.value = _state.value?.copy(mensaje = "Error al borrar Site")
            }
        }
    }

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}

