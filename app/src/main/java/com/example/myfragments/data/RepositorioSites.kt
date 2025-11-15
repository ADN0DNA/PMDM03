package com.example.myfragments.data

import com.example.myfragments.data.local.dao.SitesDao
import com.example.myfragments.data.local.dao.SiteScpCrossRefDao
import com.example.myfragments.data.local.entity.SiteScpCrossRef
import com.example.myfragments.data.local.entity.SiteEntity
import com.example.myfragments.data.local.entity.toSite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositorioSites @Inject constructor(
    private val sitesDao: SitesDao,
    private val siteScpCrossRefDao: SiteScpCrossRefDao
) {

    suspend fun getScpsWithSites() = siteScpCrossRefDao.getScpsWithSites()

    suspend fun getScpWithSites(scpId: Int) = siteScpCrossRefDao.getScpWithSites(scpId)

    suspend fun assignScpToSite(siteId: Int, scpId: Int) {
        siteScpCrossRefDao.insertSitScpCrossRef(SiteScpCrossRef(siteId, scpId))
    }

    suspend fun removeScpFromSite(siteId: Int, scpId: Int) {
        siteScpCrossRefDao.deleteSitScpCrossRef(siteId, scpId)
    }

    suspend fun insertSite(site: SiteEntity) = sitesDao.insertSite(site)

    suspend fun getAllSites() = sitesDao.getAllSites().map { it.toSite() }
}
