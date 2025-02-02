package com.benetatos.livepage_presentation

import androidx.compose.runtime.Stable
import com.benetatos.livepage_domain.model.Sport

sealed interface LiveGamesState {
    data object Loading : LiveGamesState

    data class Error(val errorMessage: String) : LiveGamesState

    @Stable
    data class Data(
        val sports: List<Sport>,
        val isFavoriteFilterActiveList : Map<String,Boolean> = emptyMap(),
        val isExpandedList : Map<String,Boolean> = emptyMap(),
    ) : LiveGamesState
}
