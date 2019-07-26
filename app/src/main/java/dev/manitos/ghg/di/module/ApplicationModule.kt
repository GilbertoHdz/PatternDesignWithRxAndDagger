package dev.manitos.ghg.di.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dev.manitos.ghg.GilichiApp
import dev.manitos.ghg.api.ApiModule

@Module(
  includes = [
    ApiModule::class,
    MyProvideModule::class
  ]
)
abstract class ApplicationModule {

  @Binds
  abstract fun bindApplication(app: GilichiApp): Application
}