package dev.manitos.ghg.mvp

import io.reactivex.Observable

interface GuantesView {
  fun showLoading(show: Boolean)

  fun loadImage(url: String?)

  fun enabledButton(enabled: Boolean)

  fun makeCatDescMsg()

  fun enableButtonHandle(): Observable<Boolean>

  fun updateDescriptionClicks(): Observable<Unit>

  fun refreshPhotoClicks(): Observable<Unit>
}
