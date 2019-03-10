package io.utkan.marvel.remote.character

import io.utkan.marvel.remote.model.CharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

// TODO: test
class CharactersRemoteImpl @Inject constructor(
    private val charactersService: CharactersService
) :
    CharactersRemote {
    override fun getCharacters(
        getCharactersParameters: GetCharactersParameters
    ): () -> Unit {
        val call = charactersService.characters(
            limit = getCharactersParameters.limit,
            apikey = getCharactersParameters.apikey,
            hash = getCharactersParameters.hash,
            timeStamp = getCharactersParameters.timeStamp
        )

        call.enqueue(object : Callback<CharactersResponse> {
            override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                getCharactersParameters.onFailure(t)
            }

            override fun onResponse(
                call: Call<CharactersResponse>,
                response: Response<CharactersResponse>
            ) {
                getCharactersParameters.onSuccess(response)
            }
        })
        return {
            call.cancel()
        }
    }
}