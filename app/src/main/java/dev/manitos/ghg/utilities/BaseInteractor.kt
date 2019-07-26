package dev.manitos.ghg.utilities

import io.reactivex.ObservableTransformer

abstract class BaseInteractor<U, D> {
  abstract fun getTransformer(): ObservableTransformer<U, D>
}
