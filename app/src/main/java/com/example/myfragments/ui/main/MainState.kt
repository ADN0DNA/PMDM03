package com.example.myfragments.ui.main

import com.example.myfragments.domain.modelo.Scp

data class MainState(
    val scps: List<Scp> = emptyList(),
    val isIrDetalle: Boolean = false,
    val mensaje: String? = null
    )
