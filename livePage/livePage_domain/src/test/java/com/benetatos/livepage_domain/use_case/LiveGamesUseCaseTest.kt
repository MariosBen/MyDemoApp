package com.benetatos.livepage_domain.use_case

import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.model.LiveGame
import com.benetatos.livepage_domain.repository.LiveGamesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import com.benetatos.livepage_domain.model.Sport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class LiveGamesUseCaseTest {

    private lateinit var liveGamesUseCase: LiveGamesUseCase
    private lateinit var getLiveGamesUseCase: GetLiveGamesUseCase
    private lateinit var getAllFavoriteLiveGamesUseCase: GetAllFavoriteLiveGamesUseCase


    @Before
    fun setup() {
        liveGamesUseCase = mockk<LiveGamesUseCase>(relaxed = true)
        getLiveGamesUseCase = mockk<GetLiveGamesUseCase>(relaxed = true)
        getAllFavoriteLiveGamesUseCase = mockk<GetAllFavoriteLiveGamesUseCase>(relaxed = true)
        liveGamesUseCase = LiveGamesUseCase(getLiveGamesUseCase, getAllFavoriteLiveGamesUseCase)

        coEvery { getLiveGamesUseCase() } returns flow { emit(Result.success(initialSportsData())) }
        coEvery { getAllFavoriteLiveGamesUseCase() } returns flow { emit((favoriteGames())) }
    }

    @Test
    fun `test countdown`(): Unit = runTest {
        val favoriteFilterClicked = MutableStateFlow(mapOf("Soccer" to true))
        var countCollectedItems :Int = 0
        var previousTimeRemaining :Long = 0

       val job = launch {
           liveGamesUseCase(favoriteFilterClicked).take(2).onEach { delay(10) }.collect {
               val timeFromTheCollect =
                   it.getOrNull()?.firstOrNull()?.liveGameFirstRow?.firstOrNull()?.timeRemaining
                       ?: 0
               assertEquals(true, it.isSuccess)
               assertEquals(1, it.getOrNull()?.size)
               if (countCollectedItems == 0) {
                   assertEquals(1000000, timeFromTheCollect)
               } else {
                   assertEquals(previousTimeRemaining - 1, timeFromTheCollect)
               }
               countCollectedItems++
               previousTimeRemaining =
                   it.getOrNull()?.first()?.liveGameFirstRow?.firstOrNull()?.timeRemaining ?: 0
           }
       }

        advanceUntilIdle()
        job.cancel()

    }

    @Test
    fun `test favorite`(): Unit = runTest {
        val favoriteFilterClicked = MutableStateFlow(mapOf("Soccer" to true))

        val job = launch {
            liveGamesUseCase(favoriteFilterClicked).take(2).onEach { delay(10) }.collect {

                assertEquals(true, it.isSuccess)
                assertEquals(1, it.getOrNull()?.size)
                assertTrue(
                    it.getOrNull()
                        ?.firstOrNull()?.liveGameFirstRow?.firstOrNull()?.isFavoriteGame == true
                )

            }
        }

        advanceUntilIdle()
        job.cancel()

    }

    private fun initialSportsData(): List<Sport> {
        return listOf(
            Sport(
                sportId = "Soccer",
                liveGameFirstRow = listOf(
                    LiveGame(timeRemaining = 1000000, gameId = "gfdgdg", isFavoriteGame = true),
                    LiveGame(timeRemaining = 1000000, gameId = "gfdgdg")
                ),
                liveGameSecondRow = emptyList(),
                sportIcon = null,
                sportName = "null"
            )
        )
    }

    private fun favoriteGames(): List<FavoriteLiveGame> {
        return listOf(FavoriteLiveGame(gameId = "123", sportId = "Soccer"))
    }

}