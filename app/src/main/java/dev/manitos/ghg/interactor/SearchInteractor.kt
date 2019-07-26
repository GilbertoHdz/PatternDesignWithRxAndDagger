package dev.manitos.ghg.interactor

import dev.manitos.ghg.api.ImageService
import dev.manitos.ghg.utilities.BaseInteractor
import dev.manitos.ghg.utilities.SchedulerProvider
import io.reactivex.ObservableTransformer
import javax.inject.Inject

class SearchInteractor @Inject constructor(
  private val imageService: ImageService,
  private val scheduler: SchedulerProvider
): BaseInteractor<SearchInteractor.Params, SearchInteractor.Result>() {

  private val transformer = ObservableTransformer<Params, Result> {
    it.flatMap { params ->
      imageService.getSearch(params.token)
        .toObservable()
        .map { response -> Result.Success(response.first().url) as Result }
        .onErrorReturn { e -> Result.Failure(e) }
        .subscribeOn(scheduler.io())
        .observeOn(scheduler.ui())
        .startWith(Result.InProgress)
    }
  }

  override fun getTransformer() = transformer

  data class Params(
    val token: String
  )

  sealed class Result {
    object InProgress : Result()
    data class Success(val urlStr: String) : Result()
    data class Failure(val e: Throwable) : Result()
  }
}
