package io.utkan.marvelui.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.utkan.marvelui.view.CharactersActivity

@Module
abstract class UiModule {
    @ContributesAndroidInjector
    abstract fun contributesCategoriesActivity(): CharactersActivity
}
