package dev.manitos.ghg.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.manitos.ghg.interactor.SearchInteractor
import dev.manitos.ghg.interactor.SearchInteractor.Result
import dev.manitos.ghg.utilities.CatDetail
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.net.UnknownHostException
import javax.inject.Inject

class OteloViewModel @Inject constructor(
  private val searchInteractor: SearchInteractor,
  private val disposable: CompositeDisposable
) : ViewModel() {

  private val loading = MutableLiveData<Boolean>()
  val loadPhoto = MutableLiveData<String>()
  val enabledButton = MutableLiveData<Boolean>()
  val showDetail = MutableLiveData<CatDetail>()
  private val showError = MutableLiveData<ErrorType>()

  fun isLoading(): LiveData<Boolean> = loading
  fun loadPhoto(): LiveData<String> = loadPhoto
  fun enabledButton(): LiveData<Boolean> = enabledButton
  fun showDetail(): LiveData<CatDetail> = showDetail
  fun showError(): LiveData<ErrorType> = showError

  fun urlImageProvide() {
    Observable.just(SearchInteractor.Params(
      token ="a1afc7c5-5b5b-4652-a92d-a63833001aae"))
      .compose(searchInteractor.getTransformer())
      .subscribe(this::updateUiImageResult)
      .addTo(disposable)
  }

  private fun updateUiImageResult(result: Result) {
    loading.value = result is Result.InProgress

    if (result is Result.Success) {
      loadPhoto.value = result.urlStr
    }

    if (result is Result.Failure) {
      Log.e(this::class.java.simpleName, "", result.e)
      showError.value = if (result.e is UnknownHostException) ErrorType.HttpError  else ErrorType.OtherError
    }
  }

  override fun onCleared() {
    disposable.clear()
    super.onCleared()
  }

  sealed class ErrorType {
    object HttpError: ErrorType()
    object OtherError: ErrorType()
  }
}
