package com.example.myfragments.data

import com.example.myfragments.data.local.dao.ScpsDao
import com.example.myfragments.data.local.entity.toScp
import com.example.myfragments.data.local.entity.toScpEntity
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioScps @Inject constructor(private val scpDao: ScpsDao) {

    suspend fun getScps(): List<Scp> = scpDao.getAllScps().map { it.toScp() }

    suspend fun getScp(id: Int): Scp? = scpDao.getScpById(id)?.toScp()

    suspend fun addScp(scp: Scp): Scp {
        val entity = scp.toScpEntity()
        val newId = scpDao.insertScp(entity)
        return scp.copy(id = newId.toInt())
    }

    suspend fun updateScp(id: Int, newScp: Scp): Boolean {
        return try {
            val entity = newScp.copy(id = id).toScpEntity()
            scpDao.updateScp(entity)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun borrar(id: Int): Boolean {
        return try {
            scpDao.deleteScpById(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
