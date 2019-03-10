package io.utkan.marvelui.di.module

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.utkan.marvel.remote.character.CharactersRemote
import io.utkan.marvel.remote.character.CharactersRemoteImpl
import io.utkan.marvel.remote.character.CharactersService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton

@Module
class RemoteModule {

    companion object {
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = CPU_COUNT + 1
        private val THREAD_POOL_EXECUTOR = Executors.newFixedThreadPool(CORE_POOL_SIZE)
    }

        @Provides
        @Named("baseUrl")
        fun providesBaseUrl() = byteArrayOf(104, 116, 116, 112, 115, 58, 47, 47, 103, 97, 116, 101, 119, 97, 121, 46, 109, 97, 114, 118, 101, 108, 46, 99, 111, 109, 47)

        @Provides
        @Singleton
        fun provideCharactersRemote(charactersService: CharactersService): CharactersRemote {
            return CharactersRemoteImpl(charactersService)
        }

        @Provides
        @Singleton
        fun provideCharactersService(retrofit: Retrofit): CharactersService = retrofit.create(
            CharactersService::class.java)

        @Provides
        @Singleton
        fun provideRetrofit(@Named("baseUrl") baseUrl: ByteArray, client: OkHttpClient, moshi: Moshi): Retrofit =
            Retrofit.Builder()
                .baseUrl(String(baseUrl))
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .callbackExecutor(THREAD_POOL_EXECUTOR)
                .build()

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

        @Provides
        @Singleton
        fun provideMoshi(): Moshi = Moshi.Builder().build()
}
