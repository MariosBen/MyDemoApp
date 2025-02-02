package com.benetatos.livepage_domain.use_case

import javax.inject.Inject


data class LiveGamesAllUseCases @Inject constructor (
    val addFavoriteLiveGameUseCase: AddFavoriteLiveGameUseCase,
    val deleteFavoriteLiveGameUseCase: DeleteFavoriteLiveGameUseCase,
    val getAllFavoriteLiveGamesUseCase: GetAllFavoriteLiveGamesUseCase,
    val getLiveGamesUseCase: GetLiveGamesUseCase,
    val liveGamesUseCase: LiveGamesUseCase,
)
