package com.example.myfragments.domain.usecases.scps

import com.example.myfragments.data.RepositorioScps
import javax.inject.Inject

class BorrarScpUseCase @Inject constructor(
    private val repo: RepositorioScps
) {
    operator fun invoke(id: Int): Boolean {
        return repo.borrar(id)
    }
}
