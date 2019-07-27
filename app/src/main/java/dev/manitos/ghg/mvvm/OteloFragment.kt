package dev.manitos.ghg.mvvm

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.jakewharton.rxbinding3.widget.textChanges
import dagger.android.support.AndroidSupportInjection

import dev.manitos.ghg.R
import dev.manitos.ghg.utilities.CatDetail
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

class OteloFragment: Fragment() {

  @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

  private lateinit var viewModel: OteloViewModel
  private val disposables = CompositeDisposable()

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProviders
      .of(this, viewModelFactory)
      .get(OteloViewModel::class.java).apply {
        val fragment = this@OteloFragment

        isLoading().observe(fragment, isLoadingObserver)
        loadPhoto().observe(fragment, loadPhotoObserver)
        enabledButton().observe(fragment, isEnabledButtonObserver)
        showDetail().observe(fragment, showDetailObserver)
        showError().observe(fragment, showErrorObserver)
      }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_cat, container, false)

  override fun onStart() {
    super.onStart()

    chkCatEnableButton.checkedChanges()
      .subscribe { viewModel.enabledButton.value = it }
      .addTo(disposables)

    btnCatRefreshPhoto.clicks()
      .subscribe { viewModel.urlImageProvide() }
      .addTo(disposables)

    btnCatUpdateDesc.clicks()
      .map {
        CatDetail(
          name = etCatName.text.toString(),
          color = etCatColor.text.toString(),
          favFood = etCatFavFood.text.toString(),
          photoUrl = viewModel.loadPhoto.value ?: ""
        )
      }
      .subscribe { detail ->
        viewModel.showDetail.value = detail
      }
      .addTo(disposables)

    Observable.merge(
      etCatName.textChanges().map { str -> str.isEmpty() } ,
      etCatColor.textChanges().map { str -> str.isEmpty() },
      etCatFavFood.textChanges().map { str -> str.isEmpty() })
      .subscribe {

      }
      .addTo(disposables)


    viewModel.urlImageProvide()
  }

  override fun onStop() {
    disposables.clear()
    super.onStop()
  }

  private val isLoadingObserver = Observer<Boolean> { isLoading ->
    if (isLoading) pbCatLoading.show() else pbCatLoading.hide()
  }

  private val loadPhotoObserver = Observer<String> { urlPhoto ->
    Glide.with(this)
      .load(urlPhoto)
      .placeholder(R.color.withe)
      .into(ivCatPhoto)
  }

  private val isEnabledButtonObserver = Observer<Boolean> {
      enabled -> btnCatUpdateDesc.isEnabled = enabled
  }

  private val showDetailObserver = Observer<CatDetail> { detail ->
    val (name, color, favFood, photoUrl) = detail

    Toast.makeText(
      requireContext(),
      "$name $color $favFood $photoUrl",
      Toast.LENGTH_SHORT
    ).show()
  }

  private val showErrorObserver = Observer<OteloViewModel.ErrorType> { error ->
    val errorMsg = when (error) {
      OteloViewModel.ErrorType.HttpError -> "upss... try again and check your connection network"
      OteloViewModel.ErrorType.OtherError -> "we can't load your image"
    }

    Toast.makeText(
      requireContext(),
      errorMsg,
      Toast.LENGTH_SHORT
    ).show()
  }

  companion object {
    fun newInstance() = OteloFragment()
  }
}
