package com.benetatos.livepage_domain.use_case

import com.benetatos.core.util.toFormatTime
import com.benetatos.livepage_domain.model.Sport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LiveGamesUseCase @Inject constructor (
    private val getLiveGamesUseCase: GetLiveGamesUseCase,
    private val getAllFavoriteLiveGamesUseCase: GetAllFavoriteLiveGamesUseCase
) {
    suspend operator fun invoke(favoriteFilterClicked: MutableStateFlow<Map<String, Boolean>>): Flow<Result<List<Sport>>> {

        val liveGamesFlow = flow {
            val initialGames = getLiveGamesUseCase()
                .first()
                .getOrElse { throw it }


            var sports = initialGames

            while (sports.any { liveGames ->
                    liveGames.liveGameFirstRow.any {
                        (it.timeRemaining ?: 0) > 0
                    } || sports.any { liveGame ->
                        liveGame.liveGameSecondRow.any {
                            (it.timeRemaining ?: 0) > 0
                        }
                    }
                }) {
                emit(sports)

                delay(1000L)

                sports = sports.map { liveGameItem ->
                    liveGameItem.copy(
                        liveGameFirstRow = liveGameItem.liveGameFirstRow.map { game ->
                            game.copy(
                                timeRemaining = (game.timeRemaining?.minus(1))?.coerceAtLeast(
                                    0
                                ),
                                timeRemainingFormated = (game.timeRemaining?.minus(1))?.coerceAtLeast(
                                    0
                                ).toFormatTime()
                            )
                        },
                        liveGameSecondRow = liveGameItem.liveGameSecondRow.map { game ->
                            game.copy(
                                timeRemaining = (game.timeRemaining?.minus(1))?.coerceAtLeast(
                                    0
                                ),
                                timeRemainingFormated = (game.timeRemaining?.minus(1))?.coerceAtLeast(
                                    0
                                ).toFormatTime()
                            )
                        }
                    )
                }
            }

            emit(sports)
        }.flowOn(Dispatchers.IO)

        return combine(liveGamesFlow, getAllFavoriteLiveGamesUseCase(),favoriteFilterClicked) { liveGames, favorites ,favoriteFilter ->
            val favoriteGameIds = favorites.map { it.gameId }.toSet()
            val favoriteFilterActive = favoriteFilter.filter { it.value}.map { it.key }.toSet()
            val updatedGames = liveGames.map { liveGameItem ->
                if (liveGameItem.sportId in favoriteFilterActive) {

                    val firstList = liveGameItem.liveGameFirstRow.map { game ->
                        game.copy(isFavoriteGame = game.gameId in favoriteGameIds)
                    }.filter { it.isFavoriteGame == true}

                    val secondList = liveGameItem.liveGameSecondRow.map { game ->
                        game.copy(isFavoriteGame = game.gameId in favoriteGameIds)
                    }.filter { it.isFavoriteGame == true}

                    val updatedList = listOf(firstList,secondList).flatten()

                    val sportsChunked = updatedList.chunked(4)
                    val firstRowList = sportsChunked.filterIndexed { index, _ -> index % 2 == 0 }.flatten()
                    val secondRowList = sportsChunked.filterIndexed { index, _ -> index % 2 != 0 }.flatten()


                    liveGameItem.copy(
                        liveGameFirstRow = firstRowList ,
                        liveGameSecondRow = secondRowList
                    )
                } else {
                    liveGameItem.copy(
                        liveGameFirstRow = liveGameItem.liveGameFirstRow.map { game ->
                            game.copy(isFavoriteGame = game.gameId in favoriteGameIds)
                        },
                        liveGameSecondRow = liveGameItem.liveGameSecondRow.map { game ->
                            game.copy(isFavoriteGame = game.gameId in favoriteGameIds)
                        }
                    )
                }
            }
            Result.success(updatedGames)
        }.catch { error ->
            emit(Result.failure(error))
        }
    }


}