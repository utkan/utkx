package io.utkan.marvel.remote.character

interface CharactersRemote {
    fun getCharacters(getCharactersParameters: GetCharactersParameters): () -> Unit
}