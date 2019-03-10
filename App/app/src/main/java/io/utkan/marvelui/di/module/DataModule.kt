package io.utkan.marvelui.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.utkan.marvel.data.ModelMapper
import io.utkan.marvel.data.character.*
import io.utkan.marvel.data.character.db.AppDatabase
import io.utkan.marvel.data.model.CharacterData
import io.utkan.marvel.remote.model.Result
import javax.inject.Named

@Module
abstract class DataModule {
    @Binds
    @Named("characterDataMapper")
    abstract fun bindCharactersDataMapperRepository(charactersDataMapper: CharacterDataMapper): ModelMapper<Result, CharacterData>

    @Binds
    abstract fun bindCharactersRemoteRepository(charactersRemoteRepository: CharactersRemoteRepositoryImpl): CharactersRemoteRepository

    @Binds
    abstract fun bindCharacterViewData(characterViewData: CharacterViewDataImpl): CharacterViewData

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun providesAppDatabase(application: Application) =
            AppDatabase.getInMemoryDatabase(application)
    }
}