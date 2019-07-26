package dev.manitos.ghg.redux

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import dagger.android.support.AndroidSupportInjection

import dev.manitos.ghg.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.fragment_cat.btnCatRefreshPhoto
import kotlinx.android.synthetic.main.fragment_cat.btnCatUpdateDesc
import kotlinx.android.synthetic.main.fragment_cat.chkCatEnableButton
import kotlinx.android.synthetic.main.fragment_cat.etCatColor
import kotlinx.android.synthetic.main.fragment_cat.etCatFavFood
import kotlinx.android.synthetic.main.fragment_cat.etCatName
import kotlinx.android.synthetic.main.fragment_cat.ivCatPhoto
import kotlinx.android.synthetic.main.fragment_cat.pbCatLoading
import javax.inject.Inject

class OrionFragment: Fragment() {

  @Inject lateinit var presenter: OrionPresenter
  @Inject lateinit var disposable: CompositeDisposable

  private var catPhotoUrl: String? = null

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_cat, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    presenter
      .present(uiEvents(), OrionUiModel(isLoaderVisible = true))
      .subscribe(this::render)
      .addTo(disposable)
  }

  override fun onDestroyView() {
    disposable.clear()
    super.onDestroyView()
  }

  private fun uiEvents(): Observable<OrionUiEvent> {
    return Observable.merge(
      btnCatUpdateDesc.clicks().map {
          OrionUiEvent.SendDescription(
            name = etCatName.text.toString(),
            color = etCatColor.text.toString(),
            favFood = etCatFavFood.text.toString(),
            photoUrl = catPhotoUrl ?: throw IllegalArgumentException("shouldn't")
          )
        },
      btnCatRefreshPhoto.clicks().map {
          OrionUiEvent.RefreshPhoto(
            someIdOrValue = "a1afc7c5-5b5b-4652-a92d-a63833001aae")
        },
      chkCatEnableButton
        .checkedChanges()
        .map { enabled -> OrionUiEvent.EnabledButton(enabled) }
    ).startWith(OrionUiEvent.RefreshPhoto("a1afc7c5-5b5b-4652-a92d-a63833001aae"))
  }

  private fun render(uiModel: OrionUiModel) {
    if (uiModel.isLoaderVisible) pbCatLoading.show() else pbCatLoading.hide()
    btnCatUpdateDesc.isEnabled = uiModel.isEnabledButtonDesc

    if (uiModel.showDescription) {
      uiModel.description ?: throw IllegalArgumentException("should'n be null")
      val (name, color, favFood, photoUrl) = uiModel.description

      Toast.makeText(
        requireContext(),
        "$name $color $favFood $photoUrl",
        Toast.LENGTH_SHORT
      ).show()
      return
    }

    if (uiModel.showPhoto) {
      catPhotoUrl = uiModel.photoUrl
      Glide.with(this)
        .load(uiModel.photoUrl)
        .placeholder(R.color.withe)
        .into(ivCatPhoto)
      return
    }

    if (uiModel.showError) {
      Log.e(this::class.java.simpleName, "", uiModel.error)
      Toast.makeText(
        requireContext(),
        "we can't load your image",
        Toast.LENGTH_SHORT
      ).show()
      return
    }
  }

  companion object {
    fun newInstance() = OrionFragment()
  }
}
