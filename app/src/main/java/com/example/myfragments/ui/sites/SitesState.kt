package com.example.myfragments.ui.sites

import com.example.myfragments.domain.modelo.Site

data class SitesState(
    val sites: List<Site> = emptyList(),
    val mensaje: String? = null
)

