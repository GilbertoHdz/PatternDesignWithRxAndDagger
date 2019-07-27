package dev.manitos.ghg.di.module

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class CatViewModelFactory @Inject constructor(
  private val creators: Map<Class<out ViewModel>,
      @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val creator: Provider<out ViewModel>? = creators[modelClass] ?: creators.asIterable()
      .firstOrNull {
        modelClass.isAssignableFrom(it.key)
      }?.value ?: throw IllegalArgumentException("Unknown ViewModel class $modelClass")

    try {
      return creator!!.get() as T
    } catch (e: Exception) {
      Log.i("", "Error with ViewModel class $modelClass")
      throw RuntimeException(e)
    }
  }
}
