package com.example.myfragments.ui.editar

import com.example.myfragments.domain.modelo.Scp

data class EditarState(
    val scp: Scp = Scp(),
    val mensaje: String? = null,
    val cerrar: Boolean = false
)
