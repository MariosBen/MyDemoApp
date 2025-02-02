package com.benetatos.livepage_domain.repository

import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.model.Sport
import kotlinx.coroutines.flow.Flow

interface LiveGamesRepository {

    suspend fun getLiveGames() : Result<List<Sport>>

    suspend fun insertFavoriteLiveGames(favoriteLiveGame : FavoriteLiveGame)

    suspend fun deleteFavoriteLiveGames(favoriteLiveGame : FavoriteLiveGame)

    suspend fun getAllFavoriteLiveGames() : Flow<List<FavoriteLiveGame>>
}