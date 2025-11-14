package com.example.myfragments.ui.editar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfragments.domain.modelo.Scp
import com.example.myfragments.domain.usecases.scps.AddScpUseCase
import com.example.myfragments.domain.usecases.scps.BorrarScpUseCase
import com.example.myfragments.domain.usecases.scps.UpdateScpUseCase
import com.example.myfragments.domain.usecases.scps.VerScpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditarViewModel @Inject constructor(
    private val addScp: AddScpUseCase,
    private val borrarScp: BorrarScpUseCase,
    private val verScp: VerScpUseCase,
    private val updateScp: UpdateScpUseCase
) : ViewModel() {

    private val _state = MutableLiveData(EditarState())
    val state: LiveData<EditarState> get() = _state

    private var currentId: Int = 0

    fun cargar(id: Int) {
        currentId = id
        val scp = try {
            verScp(id)
        } catch (t: Throwable) {
            Scp()
        }
        _state.value = _state.value?.copy(scp = scp, cerrar = false)
    }

    fun guardar(scp: Scp) {
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

    fun borrar() {
        if (currentId == 0) {
            _state.value = _state.value?.copy(mensaje = "No hay SCP seleccionado")
            return
        }
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

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}
