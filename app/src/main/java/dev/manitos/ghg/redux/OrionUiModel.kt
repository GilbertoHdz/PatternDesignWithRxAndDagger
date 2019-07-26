package dev.manitos.ghg.redux

import dev.manitos.ghg.utilities.CatDetail

data class OrionUiModel(
  val isLoaderVisible: Boolean = false,
  val isEnabledButtonDesc: Boolean = false,
  val description: CatDetail? = null,
  val showDescription: Boolean = false,
  val photoUrl: String? = null,
  val showPhoto: Boolean = false,
  val error: Throwable? = null,
  val showError: Boolean = false
)
