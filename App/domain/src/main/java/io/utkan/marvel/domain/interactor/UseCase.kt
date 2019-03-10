package io.utkan.marvel.domain.interactor

import io.utkan.marvel.domain.model.CharacterDomain

interface UseCase {
    fun execute(
        failure: (Throwable) -> Unit,
        success: (List<CharacterDomain>) -> Unit
    ): () -> Unit

    fun execute(
        limit: Int,
        failure: (Throwable) -> Unit,
        success: (List<CharacterDomain>) -> Unit
    ): () -> Unit
}
