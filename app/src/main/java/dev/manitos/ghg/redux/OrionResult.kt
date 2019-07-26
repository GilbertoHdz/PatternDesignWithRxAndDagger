package dev.manitos.ghg.redux

import dev.manitos.ghg.utilities.CatDetail

sealed class OrionResult {
  data class Detail(val detail: CatDetail): OrionResult()
  data class PhotoUrl(val string: String): OrionResult()
  data class ButtonState(val enabled: Boolean): OrionResult()
}