package com.example.myfragments.ui.scps.editar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.domain.modelo.Scp
import com.example.myfragments.domain.usecases.scps.AddScpUseCase
import com.example.myfragments.domain.usecases.scps.BorrarScpUseCase
import com.example.myfragments.domain.usecases.scps.UpdateScpUseCase
import com.example.myfragments.domain.usecases.scps.VerScpUseCase
import com.example.myfragments.data.RepositorioSites
import com.example.myfragments.ui.SiteSelectorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditarScpViewModel @Inject constructor(
    private val addScp: AddScpUseCase,
    private val borrarScp: BorrarScpUseCase,
    private val verScp: VerScpUseCase,
    private val updateScp: UpdateScpUseCase,
    private val repositorioSites: RepositorioSites
) : ViewModel() {

    private val _state = MutableLiveData(EditarScpState())
    val state: LiveData<EditarScpState> get() = _state

    private var currentId: Int = 0

    fun cargar(id: Int) {
        currentId = id
        viewModelScope.launch {
            val scp = try {
                verScp(id)
            } catch (t: Throwable) {
                Scp()
            }

            val allSites = repositorioSites.getAllSites()
            val scpWithSites = try {
                repositorioSites.getScpWithSites(id)
            } catch (t: Throwable) {
                null
            }

            val assignedSiteIds = scpWithSites?.sites?.map { it.id } ?: emptyList()

            val selectorItems = allSites.map { site ->
                SiteSelectorItem(
                    site = site,
                    isSelected = assignedSiteIds.contains(site.id)
                )
            }

            _state.value = _state.value?.copy(
                scp = scp,
                availableSites = selectorItems,
                cerrar = false
            )
        }
    }

    fun toggleSiteAssignment(site: com.example.myfragments.domain.modelo.Site, isSelected: Boolean) {
        viewModelScope.launch {
            try {
                if (isSelected) {
                    repositorioSites.assignScpToSite(site.id, currentId)
                } else {
                    repositorioSites.removeScpFromSite(site.id, currentId)
                }
                cargar(currentId)
            } catch (t: Throwable) {
                _state.value = _state.value?.copy(
                    mensaje = "Error al asignar Site: ${t.message}"
                )
            }
        }
    }

    fun guardar(scp: Scp) {
        viewModelScope.launch {
            if (currentId != 0) {
                val ok = try {
                    updateScp(currentId, scp)
                } catch (t: Throwable) {
                    false
                }
                if (ok) {
                    _state.value = _state.value?.copy(mensaje = "SCP actualizado", cerrar = true)
                } else {
                    _state.value = _state.value?.copy(mensaje = "Error al actualizar")
                }
            } else {
                val added = try {
                    addScp(scp)
                } catch (t: Throwable) {
                    null
                }
                if (added != null) {
                    _state.value = _state.value?.copy(scp = added, mensaje = "SCP creado", cerrar = true)
                } else {
                    _state.value = _state.value?.copy(mensaje = "Error al crear SCP")
                }
            }
        }
    }

    fun borrar() {
        if (currentId == 0) {
            _state.value = _state.value?.copy(mensaje = "No hay SCP seleccionado")
            return
        }
        viewModelScope.launch {
            val ok = try {
                borrarScp(currentId)
            } catch (t: Throwable) {
                false
            }
            if (ok) {
                _state.value = _state.value?.copy(mensaje = "SCP borrado", cerrar = true)
            } else {
                _state.value = _state.value?.copy(mensaje = "Error al borrar SCP")
            }
        }
    }

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}
