package com.example.myfragments.data

import com.example.myfragments.domain.modelo.Clase
import com.example.myfragments.domain.modelo.Classification
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioScps @Inject constructor() {

    private val scps = mutableListOf<Scp>()

    init {
        scps.add(Scp(1, 173, "La estatua", Clase.EUCLID, true, Classification.RESTRICTED, "El primer SCP"))
    }

    fun getScp(id: Int): Scp? = scps.find { it.id == id }?.copy()

    fun addScp(scp: Scp): Scp {
        val newId = (scps.maxOfOrNull { it.id } ?: 0) + 1
        val newScp = scp.copy(newId)
        scps.add(newScp)
        return newScp
    }

    fun updateScp(id: Int, newScp: Scp): Boolean {
        val indice = scps.indexOfFirst { it.id == id }
        if (indice == -1) return false
        scps[indice] = newScp.copy(id = id)
        return true
    }

    fun borrar(id: Int): Boolean = scps.removeIf { it.id == id }

    fun getAll(): List<Scp> = scps.map { it.copy() }
}
