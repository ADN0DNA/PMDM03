package com.example.myfragments.ui.scps.editar

import com.example.myfragments.domain.modelo.Scp
import com.example.myfragments.ui.SiteSelectorItem

data class EditarScpState(
    val scp: Scp = Scp(),
    val availableSites: List<SiteSelectorItem> = emptyList(),
    val mensaje: String? = null,
    val cerrar: Boolean = false
)
