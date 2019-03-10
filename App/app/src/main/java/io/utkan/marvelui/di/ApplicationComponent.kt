package io.utkan.marvelui.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import io.utkan.marvelui.MarvelApplication
import io.utkan.marvelui.di.module.*
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        RemoteModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        UiModule::class,
        FragmentModule::class
    )
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: MarvelApplication)

}