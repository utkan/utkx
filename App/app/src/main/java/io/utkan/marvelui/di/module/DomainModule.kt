package io.utkan.marvelui.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.utkan.marvel.domain.interactor.CharacterViewTracker
import io.utkan.marvel.domain.interactor.CharacterViewTrackerImpl
import io.utkan.marvel.domain.interactor.GetCharacters
import io.utkan.marvel.domain.interactor.UseCase
import javax.inject.Named

@Module
abstract class DomainModule {
    @Binds
    abstract fun bindGetCharacters(getCharacters: GetCharacters): UseCase

    @Binds
    abstract fun bindCharacterViewTracker(characterViewTracker: CharacterViewTrackerImpl): CharacterViewTracker

    @Module
    companion object {
        @JvmStatic @Provides @Named("publicKey") fun providesPublicKey() = byteArrayOf(97, 97, 54, 54, 52, 48, 48, 52, 98, 53, 98, 97, 53, 99, 51, 97, 98, 100, 54, 48, 56, 48, 100, 57, 48, 57, 102, 54, 97, 57, 50, 54)
        @JvmStatic @Provides @Named("privateKey") fun providesPrivateKey() = byteArrayOf(54, 99, 100, 55, 100, 100, 101, 55, 50, 50, 101, 52, 99, 57, 50, 54, 98, 102, 50, 52, 102, 102, 55, 100, 98, 97, 102, 48, 51, 102, 99, 100, 54, 52, 55, 57, 98, 100, 100, 49)
        @JvmStatic @Provides @Named("defaultLimit") fun providesDefaultLimit() = 6
    }
}
