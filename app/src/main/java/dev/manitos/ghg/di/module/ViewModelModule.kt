package dev.manitos.ghg.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
  @Binds
  abstract fun bindViewModelFactory(
    viewModelFactory: CatViewModelFactory
  ): ViewModelProvider.Factory
}
