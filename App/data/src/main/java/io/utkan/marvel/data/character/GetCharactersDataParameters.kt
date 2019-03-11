package io.utkan.marvel.data.character

import io.utkan.marvel.data.model.CharacterData

data class GetCharactersDataParameters(
    val limit: Int,
    val apikey: String,
    val hash: String,
    val timeStamp: String,
    val checkViewCount: Boolean,
    val onFailure: (Throwable) -> Unit,
    val onSuccess: (List<CharacterData>) -> Unit
)