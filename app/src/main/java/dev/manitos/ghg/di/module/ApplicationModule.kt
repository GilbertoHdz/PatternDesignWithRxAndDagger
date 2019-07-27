package dev.manitos.ghg.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dev.manitos.ghg.GilichiApp
import dev.manitos.ghg.api.ApiModule
import dev.manitos.ghg.mvp.GuantesModule
import dev.manitos.ghg.mvvm.OteloModule
import dev.manitos.ghg.redux.OrionModule
import dev.manitos.ghg.utilities.SchedulerProvider
import dev.manitos.ghg.utilities.SchedulerProviderImpl

@Module(
  includes = [
    ViewModelModule::class,
    ApiModule::class,
    MyProvideModule::class,
    GuantesModule::class,
    OrionModule::class,
    OteloModule::class
  ]
)
abstract class ApplicationModule {

  @Binds
  abstract fun bindApplication(app: GilichiApp): Application

  @Binds
  abstract fun bindSchedulerProvider(
    schedulerProviderImpl: SchedulerProviderImpl
  ): SchedulerProvider
}