package dev.manitos.ghg.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import dagger.android.support.AndroidSupportInjection

import dev.manitos.ghg.R
import kotlinx.android.synthetic.main.fragment_cat.btnCatRefreshPhoto
import kotlinx.android.synthetic.main.fragment_cat.btnCatUpdateDesc
import kotlinx.android.synthetic.main.fragment_cat.chkCatEnableButton
import kotlinx.android.synthetic.main.fragment_cat.etCatColor
import kotlinx.android.synthetic.main.fragment_cat.etCatFavFood
import kotlinx.android.synthetic.main.fragment_cat.etCatName
import kotlinx.android.synthetic.main.fragment_cat.ivCatPhoto
import kotlinx.android.synthetic.main.fragment_cat.pbCatLoading
import javax.inject.Inject

class GuantesFragment: Fragment(), GuantesView {

  @Inject lateinit var presenter: GuantesPresenter

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
    presenter.attachView(this)
  }

  override fun onStart() {
    super.onStart()
    presenter.urlImageProvide()
  }

  override fun onDestroyView() {
    presenter.detachView()
    super.onDestroyView()
  }

  override fun showLoading(show: Boolean) {
    if (show) pbCatLoading.show() else pbCatLoading.hide()
  }

  override fun loadImage(url: String?) {
    catPhotoUrl = url
    Glide.with(this)
      .load(url)
      .placeholder(R.color.withe)
      .into(ivCatPhoto)
  }

  override fun enabledButton(enabled: Boolean) {
    btnCatUpdateDesc.isEnabled = enabled
  }

  override fun makeCatDescMsg() {
    val (name, color, favFood, photoUrl) = listOf(
      etCatName.text.toString(),
      etCatColor.text.toString(),
      etCatFavFood.text.toString(),
      catPhotoUrl
    )

    Toast.makeText(
      requireContext(),
      "$name $color $favFood $photoUrl",
      Toast.LENGTH_SHORT
    ).show()
  }

  override fun updateDescriptionClicks() = btnCatUpdateDesc.clicks()

  override fun refreshPhotoClicks() = btnCatRefreshPhoto.clicks()

  override fun enableButtonHandle() = chkCatEnableButton.checkedChanges()

  companion object {
    fun newInstance() = GuantesFragment()
  }
}
