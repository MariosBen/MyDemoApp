package com.benetatos.livepage_presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.benetatos.livepage_presentation.UiEvent
import com.benetatos.livepage_presentation.LiveGamesState
import kotlin.reflect.KFunction1

@Composable
fun LivePage(
    modifier: Modifier = Modifier,
    data: LiveGamesState.Data,
    onEvent: KFunction1<UiEvent, Unit>
) {

    if (data.sports.isEmpty()) {
        Text(text = "No sports found")
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(data.sports.size) { i ->
                val sportId = data.sports[i].sportId
                SportView(
                    sport = data.sports[i],
                    isFavoriteFilterActivated = data.isFavoriteFilterActiveList[sportId],
                    isViewExpanded = data.isExpandedList[sportId],
                    onEvent = onEvent
                )
            }
        }
    }
}