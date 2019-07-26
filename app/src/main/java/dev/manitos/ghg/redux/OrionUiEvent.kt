package dev.manitos.ghg.redux

sealed class OrionUiEvent {
  data class EnabledButton(
    val enabled: Boolean
  ): OrionUiEvent()
  data class SendDescription(
    val name: String,
    val color: String,
    val favFood: String,
    val photoUrl: String
  ): OrionUiEvent()
  data class RefreshPhoto(
    val someIdOrValue: String
  ): OrionUiEvent()
}