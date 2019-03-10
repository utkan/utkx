package io.utkan.marvel.domain.interactor

import io.utkan.marvel.data.character.CharactersRemoteRepository
import io.utkan.marvel.data.character.GetCharactersDataParameters
import io.utkan.marvel.domain.CharacterDomainMapper
import io.utkan.marvel.domain.HashCalculator
import io.utkan.marvel.domain.TimeStampProvider
import io.utkan.marvel.domain.model.CharacterDomain
import javax.inject.Inject
import javax.inject.Named

class GetCharacters @Inject constructor(
    private val charactersRemoteRepository: CharactersRemoteRepository,
    @Named("publicKey") private val apiKey: ByteArray,
    @Named("privateKey") private val privateKey: ByteArray,
    @Named("defaultLimit") private val limit: Int,
    private val timeStampProvider: TimeStampProvider,
    private val hashCalculator: HashCalculator,
    private val characterDomainMapper: CharacterDomainMapper
) :
    UseCase {
    override fun execute(
        failure: (Throwable) -> Unit,
        success: (List<CharacterDomain>) -> Unit
    ): () -> Unit {
        val timeStamp = timeStampProvider.timeStamp
        val apiKey = String(apiKey)

        val hash = hashCalculator.calculate(
            timeStamp,
            String(privateKey),
            apiKey
        )
        return charactersRemoteRepository.getCharacters(
            GetCharactersDataParameters(
                limit = limit,
                apikey = apiKey,
                hash = hash ?: "",
                timeStamp = timeStamp,
                onFailure = {
                    failure(it)
                },
                onSuccess = {
                    success(it.map { characterDomainMapper.map(it) })
                }
            )
        )
    }
}
