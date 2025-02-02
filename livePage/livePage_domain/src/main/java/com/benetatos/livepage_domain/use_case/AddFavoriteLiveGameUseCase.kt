package com.benetatos.livepage_domain.use_case

import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import javax.inject.Inject

class AddFavoriteLiveGameUseCase @Inject constructor(
    private val repository: LiveGamesRepository
) {

    suspend operator fun invoke(favoriteLiveGame: FavoriteLiveGame){
        repository.insertFavoriteLiveGames(favoriteLiveGame)
    }
}