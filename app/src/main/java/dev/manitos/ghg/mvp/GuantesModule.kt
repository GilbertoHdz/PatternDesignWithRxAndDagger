package dev.manitos.ghg.mvp

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class GuantesModule {
  @ContributesAndroidInjector
  abstract fun contributeGuantesFragment(): GuantesFragment
}
