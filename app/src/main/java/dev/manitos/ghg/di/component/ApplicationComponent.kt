package dev.manitos.ghg.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.manitos.ghg.GilichiApp
import dev.manitos.ghg.di.module.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class
  ]
)
interface ApplicationComponent: AndroidInjector<GilichiApp> {

}