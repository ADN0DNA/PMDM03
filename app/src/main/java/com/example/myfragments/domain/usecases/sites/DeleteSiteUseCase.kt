package com.example.myfragments.domain.usecases.sites

import com.example.myfragments.data.local.dao.SitesDao
import javax.inject.Inject

class DeleteSiteUseCase @Inject constructor(
    private val siteDao: SitesDao
) {
    suspend operator fun invoke(id: Int): Boolean {
        return try {
            siteDao.getSiteById(id)?.let { site ->
                siteDao.deleteSite(site)
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}

