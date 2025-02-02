package com.benetatos.livepage_data.remote.repository

import com.benetatos.livepage_data.remote.LiveGamesApi
import com.benetatos.livepage_data.remote.local.FavoriteGamesDao
import com.benetatos.livepage_data.remote.mapper.toFavoriteGameEntity
import com.benetatos.livepage_data.remote.mapper.toFavoriteLiveGame
import com.benetatos.livepage_data.remote.mapper.toLiveGamesList
import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.model.Sport
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LiveGamesRepositoryImpl @Inject constructor (
    private val dao: FavoriteGamesDao,
    private val api: LiveGamesApi
) : LiveGamesRepository {

    override suspend fun getLiveGames(): Result<List<Sport>> {
        return try {
            val listOfLiveGamesDto = api.getLiveGames()
            Result.success(listOfLiveGamesDto.toLiveGamesList())

        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertFavoriteLiveGames(favoriteLiveGame: FavoriteLiveGame) {
       dao.insertFavoriteGame(favoriteLiveGame.toFavoriteGameEntity())
    }

    override suspend fun deleteFavoriteLiveGames(favoriteLiveGame: FavoriteLiveGame) {
        val data = favoriteLiveGame.toFavoriteGameEntity()
       dao.deleteFavoriteGame(data.favoriteGameId, data.sportId)
    }

    override suspend fun getAllFavoriteLiveGames(): Flow<List<FavoriteLiveGame>> {
        return dao.getFavoriteGames().map { entities -> entities.map { it.toFavoriteLiveGame() } }
    }
}