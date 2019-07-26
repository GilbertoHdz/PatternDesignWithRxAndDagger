package dev.manitos.ghg.mvp

import android.util.Log
import dev.manitos.ghg.api.ImageService
import dev.manitos.ghg.utilities.BasePresenter
import dev.manitos.ghg.utilities.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GuantesPresenter @Inject constructor(
  private val imageService: ImageService,
  private val scheduler: SchedulerProvider,
  private val disposable: CompositeDisposable
): BasePresenter<GuantesView>() {

  override fun attachView(view: GuantesView) {
    super.attachView(view)

    view.refreshPhotoClicks()
      .subscribe {
        view.showLoading(show = true)
        view.loadImage(url = null)
        urlImageProvide()
      }
      .addTo(disposable)

    view.enableButtonHandle()
      .subscribe {
        view.enabledButton(it)
      }
      .addTo(disposable)

    view.updateDescriptionClicks()
      .subscribe {
        view.makeCatDescMsg()
      }
      .addTo(disposable)
  }

  fun urlImageProvide() {
    imageService
      .getSearch("a1afc7c5-5b5b-4652-a92d-a63833001aae")
      .delay(500, TimeUnit.MILLISECONDS)
      .subscribeOn(scheduler.io())
      .observeOn(scheduler.ui())
      .subscribe({ search ->
        view.loadImage(search.first().url)
        view.showLoading(show = false)
      }, { e ->
        view.showLoading(show = false)
        Log.e(this::class.java.simpleName, "", e)
      })
      .addTo(disposable)
  }

  override fun detachView() {
    disposable.clear()
    super.detachView()
  }
}
