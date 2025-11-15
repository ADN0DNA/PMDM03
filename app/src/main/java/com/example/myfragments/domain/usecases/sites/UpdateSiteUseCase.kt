package com.example.myfragments.domain.usecases.sites

import com.example.myfragments.data.RepositorioSites
import com.example.myfragments.data.local.entity.toSiteEntity
import com.example.myfragments.domain.modelo.Site
import javax.inject.Inject

class UpdateSiteUseCase @Inject constructor(
    private val repositorioSites: RepositorioSites
) {
    suspend operator fun invoke(id: Int, site: Site): Boolean {
        return try {
            val entity = site.copy(id = id).toSiteEntity()
            repositorioSites.insertSite(entity)
            true
        } catch (e: Exception) {
            false
        }
    }
}

