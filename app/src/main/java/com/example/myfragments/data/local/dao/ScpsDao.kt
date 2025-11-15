package com.example.myfragments.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myfragments.data.local.entity.ScpEntity


@Dao
interface ScpsDao {
    @Query("SELECT * FROM scps")
    suspend fun getAllScps(): List<ScpEntity>

    @Query("SELECT * FROM scps WHERE id = :id")
    suspend fun getScpById(id: Int): ScpEntity?

    @Insert
    suspend fun insertScp(entity: ScpEntity): Long

    @Update
    suspend fun updateScp(entity: ScpEntity)

    @Query("DELETE FROM scps WHERE id = :id")
    suspend fun deleteScpById(id: Int)


}