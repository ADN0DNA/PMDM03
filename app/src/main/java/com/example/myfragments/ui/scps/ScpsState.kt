package com.example.myfragments.ui.scps

import com.example.myfragments.data.local.entity.ScpWithSites

data class ScpsState(
    val scps: List<ScpWithSites> = emptyList(),
    val isIrDetalle: Boolean = false,
    val mensaje: String? = null
)
