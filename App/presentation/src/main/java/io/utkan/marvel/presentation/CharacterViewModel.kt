package io.utkan.marvel.presentation

data class CharacterViewModel(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val detailImageUrl: String,
    val action: ((String) -> Unit)?
)