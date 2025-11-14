package com.example.myfragments.domain.usecases.scps


import com.example.myfragments.data.RepositorioScps
import com.example.myfragments.domain.modelo.Scp
import javax.inject.Inject

class GetAllScpsUseCase @Inject constructor(private val repo: RepositorioScps) {
    operator fun invoke(): List<Scp> {
        return repo.getAll()
    }
}