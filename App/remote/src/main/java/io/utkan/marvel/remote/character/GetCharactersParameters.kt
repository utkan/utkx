package io.utkan.marvel.remote.character

import io.utkan.marvel.remote.model.CharactersResponse
import retrofit2.Response

data class GetCharactersParameters(
    val limit: Int,
    val apikey: String,
    val hash: String,
    val timeStamp: String,
    val onFailure: (Throwable) -> Unit,
    val onSuccess: (Response<CharactersResponse>) -> Unit
)