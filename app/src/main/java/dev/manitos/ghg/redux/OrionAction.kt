package dev.manitos.ghg.redux

import dev.manitos.ghg.utilities.CatDetail

sealed class OrionAction  {
  object IsLoading : OrionAction()
  data class Detail(val detail: CatDetail): OrionAction()
  data class PhotoUrl(val string: String): OrionAction()
  data class ButtonState(val enabled: Boolean): OrionAction()
  data class Failed(val e: Throwable): OrionAction()
}