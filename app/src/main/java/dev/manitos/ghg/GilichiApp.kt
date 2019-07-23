package dev.manitos.ghg

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import dev.manitos.ghg.di.component.DaggerApplicationComponent
import javax.inject.Inject

class GilichiApp: Application(), HasActivityInjector, HasSupportFragmentInjector {

  @Inject
  lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  @Inject
  lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate() {
    super.onCreate()
    DaggerApplicationComponent.create().inject(this)
  }

  override fun activityInjector(): AndroidInjector<Activity> {
    return activityDispatchingAndroidInjector
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> {
    return fragmentDispatchingAndroidInjector
  }
}