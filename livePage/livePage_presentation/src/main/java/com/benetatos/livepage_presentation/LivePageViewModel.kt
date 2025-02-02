package com.benetatos.livepage_presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benetatos.livepage_domain.model.FavoriteLiveGame
import com.benetatos.livepage_domain.use_case.LiveGamesAllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LivePageViewModel @Inject constructor(
    private val liveGamesAllUseCases: LiveGamesAllUseCases
) : ViewModel() {
    private val expandCollapseClicked = MutableStateFlow<Map<String,Boolean>>(emptyMap())
    private val favoriteFilterClicked = MutableStateFlow<Map<String,Boolean>>(emptyMap())

    val state: StateFlow<LiveGamesState> = flow {
        combine(
            liveGamesAllUseCases.liveGamesUseCase(favoriteFilterClicked),
            expandCollapseClicked,
            favoriteFilterClicked
        ) { liveGames, expandCollapse, favoriteFilterClicked ->

            if (liveGames.isFailure) {
                LiveGamesState.Error("No Internet error")
            }

            liveGames.getOrNull()?.let {
                LiveGamesState.Data(
                    sports = it,
                    isExpandedList = expandCollapse,
                    isFavoriteFilterActiveList = favoriteFilterClicked
                )
            } ?:
            LiveGamesState.Error("Unknown error")

        }
            .catch { error ->
                Log.d("marios", error.toString())
                emit(
                    LiveGamesState.Error(
                        error.message ?: "Unknown error"
                    )
                )
            }
            .collect {
                emit(it)
            }

    }.flowOn(Dispatchers.Main)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LiveGamesState.Loading)


    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.FavoriteUnFavoriteGame -> {
                viewModelScope.launch(Dispatchers.IO) {
                    if (event.isFavorite) {
                        liveGamesAllUseCases.deleteFavoriteLiveGameUseCase(
                            FavoriteLiveGame(gameId = event.gameId, sportId = event.sportId)
                        )
                    }else{
                        liveGamesAllUseCases.addFavoriteLiveGameUseCase(
                            FavoriteLiveGame(gameId = event.gameId, sportId = event.sportId)
                        )
                    }
                }
            }
            is UiEvent.ShowOnlyFavoriteGames -> {
                viewModelScope.launch {
                    val updatedFavoriteFilterClickedItems = (state.value as LiveGamesState.Data?)?.isFavoriteFilterActiveList?.toMutableMap().apply {
                        if (this == null) return@apply
                        if (this.containsKey(event.sportId)) {
                            this[event.sportId] = !this[event.sportId]!!
                        }else{
                            this[event.sportId] = !event.isFavorite
                        }
                    }?.toMap()
                    updatedFavoriteFilterClickedItems?.let {
                        favoriteFilterClicked.emit(it)
                    }

                }
            }
            is UiEvent.ExpandCollapseSport -> {
                viewModelScope.launch {
                    val updatedExpandedCollapsedItems = (state.value as LiveGamesState.Data?)?.isExpandedList?.toMutableMap().apply {
                        if (this == null) return@apply
                        if (this.containsKey(event.sportId)) {
                            this[event.sportId] = !this[event.sportId]!!
                        }else{
                            this[event.sportId] = !event.isFavorite
                        }
                    }?.toMap()
                    updatedExpandedCollapsedItems?.let {
                        expandCollapseClicked.emit(it)
                    }
                }
            }
        }
    }
}