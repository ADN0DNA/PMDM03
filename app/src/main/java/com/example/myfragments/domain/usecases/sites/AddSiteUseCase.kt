package com.example.myfragments.domain.usecases.sites

import com.example.myfragments.data.RepositorioSites
import com.example.myfragments.data.local.entity.SiteEntity
import com.example.myfragments.data.local.entity.toSiteEntity
import com.example.myfragments.domain.modelo.Site
import javax.inject.Inject

class AddSiteUseCase @Inject constructor(
    private val repositorioSites: RepositorioSites
) {
    suspend operator fun invoke(site: Site): Site {
        val entity = site.toSiteEntity()
        val newId = repositorioSites.insertSite(entity)
        return site.copy(id = newId.toInt())
    }
}

