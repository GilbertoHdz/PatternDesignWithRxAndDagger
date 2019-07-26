package dev.manitos.ghg.redux

import dev.manitos.ghg.interactor.SearchInteractor
import dev.manitos.ghg.utilities.CatDetail
import dev.manitos.ghg.utilities.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class OrionPresenter @Inject constructor(
  private val searchInteractor: SearchInteractor,
  private val scheduler: SchedulerProvider,
  private val scheduler2: SchedulerProvider,
  private val disposable: CompositeDisposable
) {

  fun present(
    uiEvent: Observable<OrionUiEvent>,
    initialUiModel: OrionUiModel
  ): Observable<OrionUiModel> {
    return uiEvent
      .observeOn(scheduler.ui())
      .compose {
        it.publish { uiEvent ->
          Observable.merge<OrionAction>(
              uiEvent.ofType(OrionUiEvent.EnabledButton::class.java)
                .compose { it.map { OrionAction.ButtonState(it.enabled) } },
              uiEvent.ofType(OrionUiEvent.SendDescription::class.java)
                .compose { it.map {
                  OrionAction.Detail(CatDetail(it.name, it.color, it.favFood, it.photoUrl))
                } },
              uiEvent.ofType(OrionUiEvent.RefreshPhoto::class.java)
                .map { SearchInteractor.Params(it.someIdOrValue) }
                .compose(searchInteractor.getTransformer())
                .map { result ->
                  when (result) {
                    is SearchInteractor.Result.InProgress -> {
                      OrionAction.IsLoading
                    }
                    is SearchInteractor.Result.Success -> {
                      OrionAction.PhotoUrl(result.urlStr)
                    }
                    is SearchInteractor.Result.Failure -> {
                      OrionAction.Failed(result.e)
                    }
                  }
                }
          )
        }
      }
      .scan(initialUiModel, this::reduce)
  }

  private fun reduce(
    previousUiModel: OrionUiModel,
    action: OrionAction
  ): OrionUiModel {
    return when (action) {
        is OrionAction.IsLoading -> {
          previousUiModel.copy(isLoaderVisible = true)
        }
        is OrionAction.Detail -> {
          previousUiModel.copy(
            description = action.detail,
            showDescription = true,
            showPhoto = false,
            showError = false
          )
        }
        is OrionAction.PhotoUrl -> {
          previousUiModel.copy(
            isLoaderVisible = false,
            photoUrl = action.string,
            showDescription = false,
            showPhoto = true,
            showError = false
          )
        }
        is OrionAction.ButtonState -> {
          previousUiModel.copy(
            isEnabledButtonDesc = action.enabled,
            showDescription = false,
            showPhoto = false,
            showError = false
          )
        }
        is OrionAction.Failed -> {
          previousUiModel.copy(
            isLoaderVisible = false,
            showError = true,
            error = action.e
          )
        }
      }
  }
}
