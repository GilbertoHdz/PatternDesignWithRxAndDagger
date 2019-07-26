package dev.manitos.ghg.utilities

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Implementation of the {@link SchedulerProvider} interface. Provides the appropriate thread for each
 * kind of task.
 */
class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {

  override fun computation(): Scheduler = Schedulers.computation()

  override fun io(): Scheduler = Schedulers.io()

  override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}