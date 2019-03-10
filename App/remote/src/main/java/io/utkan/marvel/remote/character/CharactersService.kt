package io.utkan.marvel.remote.character

import io.utkan.marvel.remote.model.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersService {
    @GET("v1/public/characters")
    fun characters(
        @Query("limit") limit: Int?,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
        @Query("ts") timeStamp: String
    ): Call<CharactersResponse>
}