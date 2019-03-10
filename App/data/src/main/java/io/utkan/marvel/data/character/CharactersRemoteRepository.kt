package io.utkan.marvel.data.character

interface CharactersRemoteRepository {
    fun getCharacters(getCharactersDataParameters: GetCharactersDataParameters): ()->Unit
}