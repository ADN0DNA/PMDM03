package com.example.myfragments.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myfragments.data.local.entity.SiteEntity
import com.example.myfragments.data.local.entity.SiteWithScps

@Dao
interface SitesDao {
    @Query("SELECT * FROM sites")
    suspend fun getAllSites(): List<SiteEntity>

    @Query("SELECT * FROM sites WHERE id = :id")
    suspend fun getSiteById(id: Int): SiteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSite(site: SiteEntity): Long

    @Transaction
    @Query("SELECT * FROM sites")
    suspend fun getSitesWithScps(): List<SiteWithScps>

    @Transaction
    @Query("SELECT * FROM sites WHERE id = :siteId")
    suspend fun getSiteWithScps(siteId: Int): SiteWithScps?

    @Delete
    suspend fun deleteSite(site: SiteEntity)

}

