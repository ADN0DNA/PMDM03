package com.example.myfragments.domain.modelo

data class Scp(
    val id : Int = 0,
    val item: Int = 0,
    val nombre: String = "",
    val clase: Clase = Clase.SAFE,
    val favorite: Boolean = false,
    val classification: Classification = Classification.UNRESTRICTED,
    val description: String = "",
)

