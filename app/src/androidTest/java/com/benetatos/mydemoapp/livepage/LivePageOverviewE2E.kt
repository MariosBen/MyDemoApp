package com.benetatos.mydemoapp.livepage

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.benetatos.livepage_domain.model.LiveGame
import com.benetatos.livepage_domain.model.Sport
import com.benetatos.livepage_domain.use_case.AddFavoriteLiveGameUseCase
import com.benetatos.livepage_domain.use_case.DeleteFavoriteLiveGameUseCase
import com.benetatos.livepage_domain.use_case.GetAllFavoriteLiveGamesUseCase
import com.benetatos.livepage_domain.use_case.GetLiveGamesUseCase
import com.benetatos.livepage_domain.use_case.LiveGamesAllUseCases
import com.benetatos.livepage_domain.use_case.LiveGamesUseCase
import com.benetatos.livepage_presentation.LiveGameScreen
import com.benetatos.livepage_presentation.LiveGamesState
import com.benetatos.livepage_presentation.LivePageViewModel
import com.google.common.truth.Truth.assertThat
import com.benetatos.mydemoapp.MainActivity
import com.benetatos.mydemoapp.livepage.repository.LiveGamesRepositoryFake
import com.benetatos.mydemoapp.navigation.Routes
import com.benetatos.mydemoapp.ui.theme.MyDemoAppTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.Route
import okhttp3.internal.notifyAll
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class LivePageOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repositoryFake: LiveGamesRepositoryFake
    private lateinit var livePageUseCases: LiveGamesAllUseCases
    private lateinit var livePageViewModel: LivePageViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        repositoryFake = LiveGamesRepositoryFake()
        val getLiveGamesUseCase = GetLiveGamesUseCase(repositoryFake)
        val getAllFavoriteLiveGamesUseCase = GetAllFavoriteLiveGamesUseCase(repositoryFake)

        livePageUseCases = LiveGamesAllUseCases(
            addFavoriteLiveGameUseCase = AddFavoriteLiveGameUseCase(repositoryFake),
            deleteFavoriteLiveGameUseCase = DeleteFavoriteLiveGameUseCase(repositoryFake),
            getAllFavoriteLiveGamesUseCase = getAllFavoriteLiveGamesUseCase,
            getLiveGamesUseCase = getLiveGamesUseCase,
            liveGamesUseCase = LiveGamesUseCase(
                getLiveGamesUseCase = getLiveGamesUseCase,
                getAllFavoriteLiveGamesUseCase = getAllFavoriteLiveGamesUseCase
            )
        )
        livePageViewModel = LivePageViewModel(livePageUseCases)

        composeTestRule.activity.setContent {

            MyDemoAppTheme {
                LiveGameScreen(livePageViewModel)
            }
        }
    }

    @Test
    fun allElementsAreVisibleAtFirstSport() {

        var currentState : LiveGamesState= LiveGamesState.Loading

        repositoryFake.liveGames = mutableListOf(
            Sport(
                sportIcon = null,
                sportId = "FOOT",
                sportName = "SOCCER",
                liveGameFirstRow = listOf(
                    LiveGame(
                        timeRemaining = 4551883,
                        isFavoriteGame = false,
                        teamHome = "MossFK",
                        teamAway = "VikingFK",
                        gameId = "1738509024",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738784581,
                        isFavoriteGame = false,
                        teamHome = "DenmarkU19",
                        teamAway = "CzechRepublicU19",
                        gameId = "45522843",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738469794,
                        isFavoriteGame = false,
                        teamHome = "SpainU19",
                        teamAway = "NorwayU19",
                        gameId = "45522851",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738970139,
                        isFavoriteGame = false,
                        teamHome = "BarrowII",
                        teamAway = "PrestonII",
                        gameId = "45518175",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738414584,
                        isFavoriteGame = false,
                        teamHome = "SalfordCityII",
                        teamAway = "BoltonII",
                        gameId = "45518800",
                        sportId = "FOOT",
                    ),
                ),
                liveGameSecondRow = listOf(
                    LiveGame(
                        timeRemaining = 4551883,
                        isFavoriteGame = false,
                        teamHome = "MossFK",
                        teamAway = "VikingFK",
                        gameId = "1738509024",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738784581,
                        isFavoriteGame = false,
                        teamHome = "DenmarkU19",
                        teamAway = "CzechRepublicU19",
                        gameId = "45522843",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738469794,
                        isFavoriteGame = false,
                        teamHome = "SpainU19",
                        teamAway = "NorwayU19",
                        gameId = "45522851",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738970139,
                        isFavoriteGame = false,
                        teamHome = "BarrowII",
                        teamAway = "PrestonII",
                        gameId = "45518175",
                        sportId = "FOOT",
                    ),
                    LiveGame(
                        timeRemaining = 1738414584,
                        isFavoriteGame = false,
                        teamHome = "SalfordCityII",
                        teamAway = "BoltonII",
                        gameId = "45518800",
                        sportId = "FOOT",
                    ),
                )
            )
        )
        runTest {
            val test = livePageViewModel.state.value


        }






//       livePageViewModel.viewModelScope.launch {
//           livePageViewModel.state.value = LiveGamesState.Data
//       }


//        composeTestRule.waitUntil(timeoutMillis = 5_000) {
//            val test =  livePageViewModel.state.value
//            Log.d("test",test.toString())
//            livePageViewModel.state.value is LiveGamesState.Data
//        }




        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.runOnIdle {
                Log.d("Current state"," ${livePageViewModel.state.value}")
            }
            livePageViewModel.state.value is LiveGamesState.Data
        }
//
//        composeRule.waitForIdle()
//        composeRule.waitUntil(timeoutMillis = 5_000) {
//            composeRule.onNodeWithTag("favoriteFilterSwitch").isDisplayed()
//        }

//        assertThat(
//            currentState is LiveGamesState.Data
//        ).isTrue()
//
//
//        composeTestRule.onNodeWithText((currentState as? LiveGamesState.Data)?.sports?.firstOrNull()?.liveGameFirstRow?.firstOrNull()?.teamHome ?: "")
//            .assertIsDisplayed()
//
//        composeRule.onNodeWithText("MossFK").assertIsDisplayed()
//        composeRule.onNodeWithText("DenmarkU19").assertIsDisplayed()
//        composeRule.onNodeWithText("SpainU19").assertIsDisplayed()
//        composeRule.onNodeWithText("BarrowII").assertIsDisplayed()
//        composeRule.onNodeWithText("SalfordCityII").assertIsNotDisplayed()
//
//        composeRule.onNodeWithTag(testTag = "rowItem0").assertIsDisplayed()

    }

//    fun test() = runTest {
//        repositoryFake.liveGames.
//    }


}