package io.utkan.marvelui.di.module

import dagger.Binds
import dagger.Module
import io.utkan.marvel.data.ModelMapper
import io.utkan.marvel.data.character.CharacterDataMapper
import io.utkan.marvel.data.character.CharactersRemoteRepository
import io.utkan.marvel.data.character.CharactersRemoteRepositoryImpl
import io.utkan.marvel.data.model.CharacterData
import io.utkan.marvel.remote.model.Result

@Module
abstract class DataModule {
    @Binds
    abstract fun bindCharactersDataMapperRepository(charactersDataMapper: CharacterDataMapper): ModelMapper<Result, CharacterData>

    @Binds
    abstract fun bindCharactersRemoteRepository(charactersRemoteRepository: CharactersRemoteRepositoryImpl): CharactersRemoteRepository
}