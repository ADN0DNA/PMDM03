package com.example.myfragments.domain.usecases.sites

import com.example.myfragments.data.RepositorioSites
import com.example.myfragments.domain.modelo.Site
import javax.inject.Inject

class GetSiteUseCase @Inject constructor(
    private val repositorioSites: RepositorioSites
) {
    suspend operator fun invoke(id: Int): Site? {
        return repositorioSites.getAllSites().find { it.id == id }
    }
}

