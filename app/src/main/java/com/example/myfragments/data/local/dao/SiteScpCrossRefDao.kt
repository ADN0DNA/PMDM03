package com.example.myfragments.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.myfragments.data.local.entity.SiteScpCrossRef
import com.example.myfragments.data.local.entity.ScpWithSites

@Dao
interface SiteScpCrossRefDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSitScpCrossRef(crossRef: SiteScpCrossRef)

    @Query("DELETE FROM SiteScpCrossRef WHERE siteId = :siteId AND scpId = :scpId")
    suspend fun deleteSitScpCrossRef(siteId: Int, scpId: Int)

    @Transaction
    @Query("SELECT * FROM scps WHERE id = :scpId")
    suspend fun getScpWithSites(scpId: Int): ScpWithSites?

    @Transaction
    @Query("SELECT * FROM scps")
    suspend fun getScpsWithSites(): List<ScpWithSites>
}

