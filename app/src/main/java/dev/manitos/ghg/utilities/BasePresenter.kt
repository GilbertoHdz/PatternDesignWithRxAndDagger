package dev.manitos.ghg.utilities

abstract class BasePresenter<V: Any> {

  protected lateinit var view: V

  open fun attachView(view: V) {
    this.view = view
  }

  open fun detachView() {}
}
