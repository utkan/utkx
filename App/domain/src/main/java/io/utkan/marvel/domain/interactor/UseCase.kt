package io.utkan.marvel.domain.interactor

import io.utkan.marvel.domain.model.CharacterDomain

interface UseCase {
    fun execute(
        firstTime: Boolean = true,
        failure: (Throwable) -> Unit,
        success: (List<CharacterDomain>) -> Unit
    ): () -> Unit

    fun execute(
        firstTime: Boolean,
        limit: Int,
        failure: (Throwable) -> Unit,
        success: (List<CharacterDomain>) -> Unit
    ): () -> Unit
}
