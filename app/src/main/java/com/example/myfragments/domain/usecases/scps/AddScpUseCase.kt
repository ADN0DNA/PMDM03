package com.example.myfragments.domain.usecases.scps

import com.example.myfragments.data.RepositorioScps
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject

class AddScpUseCase @Inject constructor(
    private val repo: RepositorioScps
) {
    operator fun invoke(scp: Scp): Scp {
        return repo.addScp(scp)
    }
}
