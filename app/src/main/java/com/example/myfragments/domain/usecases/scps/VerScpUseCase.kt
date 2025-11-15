package com.example.myfragments.domain.usecases.scps

import com.example.myfragments.data.RepositorioScps
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject

class VerScpUseCase @Inject constructor(
    private val repo: RepositorioScps
) {
    operator suspend fun invoke(id: Int): Scp = repo.getScp(id) ?: Scp()
}