package io.utkan.marvelui.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.utkan.marvelui.view.CharactersActivityFragment

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    internal abstract fun contributeCategoriesActivityFragment(): CharactersActivityFragment
}