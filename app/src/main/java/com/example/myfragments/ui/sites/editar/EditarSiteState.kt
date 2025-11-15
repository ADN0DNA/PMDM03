package com.example.myfragments.ui.sites.editar

import com.example.myfragments.domain.modelo.Site

data class EditarSiteState(
    val site: Site = Site(0, 0, "", "", ""),
    val mensaje: String? = null,
    val cerrar: Boolean = false
)

