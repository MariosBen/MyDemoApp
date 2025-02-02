package com.benetatos.livepage_domain.use_case

import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteLiveGamesUseCase @Inject constructor (
    private val repository: LiveGamesRepository
) {
    suspend operator fun invoke() : Flow<List<FavoriteLiveGame>>{
        return repository.getAllFavoriteLiveGames()
    }
}