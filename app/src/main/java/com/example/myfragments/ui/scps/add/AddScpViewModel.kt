package com.example.myfragments.ui.scps.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfragments.domain.modelo.Scp
import com.example.myfragments.domain.usecases.scps.AddScpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AddScpViewModel @Inject constructor(
    private val addScpUseCase: AddScpUseCase
) : ViewModel() {
    private val _state = MutableLiveData(AddScpState())
    val state: LiveData<AddScpState> get() = _state

    fun guardar(scp: Scp) {
        viewModelScope.launch {
            val added = try {
                addScpUseCase(scp)
            } catch (t: Throwable) {
                null
            }

            if (added == null || added.id == 0) {
                _state.value = _state.value?.copy(
                    number = scp.item,
                    alias = scp.nombre,
                    description = scp.description,
                    mensaje = "Error al guardar SCP ${scp.item}"
                )
            } else {
                _state.value = _state.value?.copy(
                    number = added.item,
                    alias = added.nombre,
                    description = added.description,
                    mensaje = "SCP ${added.item} guardado"
                )
            }
        }
    }

    fun limpiarMensaje() {
        _state.value = _state.value?.copy(mensaje = null)
    }
}
