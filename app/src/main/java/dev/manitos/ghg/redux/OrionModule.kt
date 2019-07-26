package dev.manitos.ghg.redux

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OrionModule {

  @ContributesAndroidInjector
  abstract fun contributeOrionFragment(): OrionFragment
}
