package dev.manitos.ghg.mvvm

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dev.manitos.ghg.utilities.ViewModelKey

@Module
abstract class OteloModule {

  @ContributesAndroidInjector
  abstract fun contributeOteloFragment(): OteloFragment

  @Binds
  @IntoMap
  @ViewModelKey(OteloViewModel::class)
  abstract fun bindOteloViewModel(oteloViewModel: OteloViewModel): ViewModel
}
