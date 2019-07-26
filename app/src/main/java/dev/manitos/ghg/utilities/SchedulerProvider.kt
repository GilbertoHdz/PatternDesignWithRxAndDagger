package dev.manitos.ghg.utilities

import io.reactivex.Scheduler

/** Interface to provide different type of Rx Schedulers. */
interface SchedulerProvider {
  fun computation(): Scheduler

  fun io(): Scheduler

  fun ui(): Scheduler
}