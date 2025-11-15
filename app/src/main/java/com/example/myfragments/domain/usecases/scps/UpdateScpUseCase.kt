package com.example.myfragments.domain.usecases.scps

import com.example.myfragments.data.RepositorioScps
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject

class UpdateScpUseCase @Inject constructor(
    private val repo: RepositorioScps
) {
    suspend operator fun invoke(id: Int, scp: Scp): Boolean {
        return repo.updateScp(id, scp)
    }
}
