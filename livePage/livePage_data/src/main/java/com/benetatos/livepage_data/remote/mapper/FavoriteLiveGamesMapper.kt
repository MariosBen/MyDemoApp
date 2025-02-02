package com.benetatos.livepage_data.remote.mapper

import com.benetatos.livepage_data.remote.local.entity.FavoriteGameEntity
import com.benetatos.livepage_domain.model.FavoriteLiveGame


fun FavoriteGameEntity.toFavoriteLiveGame(): FavoriteLiveGame {
    return FavoriteLiveGame(
        sportId = sportId,
        gameId = favoriteGameId,
        id = id
    )
}

fun FavoriteLiveGame.toFavoriteGameEntity(): FavoriteGameEntity {
    return FavoriteGameEntity(
        sportId = sportId,
        favoriteGameId = gameId,
        id = id
    )
}