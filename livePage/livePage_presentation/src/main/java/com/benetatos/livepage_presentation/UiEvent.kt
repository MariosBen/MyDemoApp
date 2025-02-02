package com.benetatos.livepage_presentation

sealed class UiEvent {
    data class FavoriteUnFavoriteGame(val isFavorite : Boolean, val gameId : String ,val sportId : String) : UiEvent()
    data class ShowOnlyFavoriteGames(val isFavorite : Boolean, val sportId : String) : UiEvent()
    data class ExpandCollapseSport(val isFavorite : Boolean, val sportId : String) : UiEvent()
}