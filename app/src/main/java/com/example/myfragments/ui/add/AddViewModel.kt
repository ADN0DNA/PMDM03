package com.example.myfragments.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfragments.domain.modelo.Scp
import com.example.myfragments.domain.usecases.scps.AddScpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val addScpUseCase: AddScpUseCase
) : ViewModel() {
    private val _state = MutableLiveData(AddState())
    val state: LiveData<AddState> get() = _state

    fun setNumber(number: Int) {
        _state.value = _state.value?.copy(number = number)
    }

    fun setAlias(alias: String) {
        _state.value = _state.value?.copy(alias = alias)
    }

    fun setDescription(description: String) {
        _state.value = _state.value?.copy(description = description)
    }

    fun guardar(scp: Scp) {
        // AddScpUseCase now returns the added Scp (with assigned id) or may throw.
        val added = try {
            addScpUseCase(scp)
        } catch (t: Throwable) {
            null
        }

        if (added == null || added.id == 0) {
            // error
            _state.value = _state.value?.copy(
                number = scp.item,
                alias = scp.nombre,
                description = scp.description,
                mensaje = "Error al guardar SCP ${scp.item}"
            )
        } else {
            // success: update state with saved values and a message
            _state.value = _state.value?.copy(
                number = added.item,
                alias = added.nombre,
                description = added.description,
                mensaje = "SCP ${added.item} guardado"
            )
        }
    }

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}
