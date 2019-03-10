package io.utkan.marvel.domain.model

data class CharacterDomain(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val detailImageUrl: String
)