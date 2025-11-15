package com.example.myfragments.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfragments.data.local.dao.ScpsDao
import com.example.myfragments.data.local.dao.SitesDao
import com.example.myfragments.data.local.dao.SiteScpCrossRefDao
import com.example.myfragments.data.local.entity.ScpEntity
import com.example.myfragments.data.local.entity.SiteEntity
import com.example.myfragments.data.local.entity.SiteScpCrossRef


@Database(
    entities = [ScpEntity::class, SiteEntity::class, SiteScpCrossRef::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun scpDao(): ScpsDao
    abstract fun siteDao(): SitesDao
    abstract fun sitScpCrossRefDao(): SiteScpCrossRefDao
}
