package io.utkan.marvel.data.character


import io.utkan.marvel.data.ModelMapper
import io.utkan.marvel.data.model.CharacterData
import io.utkan.marvel.remote.character.CharactersRemote
import io.utkan.marvel.remote.character.GetCharactersParameters
import io.utkan.marvel.remote.model.Result
import javax.inject.Inject
import javax.inject.Named

class CharactersRemoteRepositoryImpl @Inject constructor(
    private val charactersRemote: CharactersRemote,
    @Named("characterDataMapper")
    private val characterDataMapper: ModelMapper<@JvmSuppressWildcards Result, @JvmSuppressWildcards CharacterData>
) : CharactersRemoteRepository {
    override fun getCharacters(getCharactersDataParameters: GetCharactersDataParameters): () -> Unit {
        return charactersRemote.getCharacters(GetCharactersParameters(
            limit = getCharactersDataParameters.limit,
            apikey = getCharactersDataParameters.apikey,
            hash = getCharactersDataParameters.hash,
            timeStamp = getCharactersDataParameters.timeStamp,
            onSuccess = { response ->
                response.takeIf { it.isSuccessful }?.let {
                    it.body()?.let { body ->
                        getCharactersDataParameters.onSuccess(body.data.results.map { result ->
                            characterDataMapper.map(result)
                        })
                    } ?: getCharactersDataParameters.onFailure(RuntimeException())
                } ?: getCharactersDataParameters.onFailure(RuntimeException())
            },
            onFailure = {
                getCharactersDataParameters.onFailure(it)
            }
        ))
    }
}
