package com.benetatos.mydemoapp.livepage.repository

import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.model.Sport
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.random.Random

class LiveGamesRepositoryFake : LiveGamesRepository {

    var shouldReturnError = false

    private val listOfFavoriteLiveGames = mutableListOf<FavoriteLiveGame>()
    var liveGames = mutableListOf<Sport>()
    private val getFavoriteLiveGamesFlow = MutableSharedFlow<List<FavoriteLiveGame>>(replay = 1)


    override suspend fun getLiveGames(): Result<List<Sport>> {
      return if (shouldReturnError) {
            Result.failure(Throwable())
        } else{
            return Result.success(liveGames)
      }
    }

    override suspend fun insertFavoriteLiveGames(favoriteLiveGame: FavoriteLiveGame) {
        listOfFavoriteLiveGames.add(favoriteLiveGame.copy(id = Random.nextInt()))
        getFavoriteLiveGamesFlow.emit(listOfFavoriteLiveGames)
    }

    override suspend fun deleteFavoriteLiveGames(favoriteLiveGame: FavoriteLiveGame) {
        listOfFavoriteLiveGames.remove(favoriteLiveGame)
        getFavoriteLiveGamesFlow.emit(listOfFavoriteLiveGames)
    }

    override suspend fun getAllFavoriteLiveGames(): Flow<List<FavoriteLiveGame>> {
      return getFavoriteLiveGamesFlow
    }
}