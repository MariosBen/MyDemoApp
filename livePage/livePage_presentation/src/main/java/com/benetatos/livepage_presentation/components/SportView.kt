package com.benetatos.livepage_presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.benetatos.livepage_presentation.UiEvent
import com.benetatos.livepage_domain.model.Sport
import kotlin.reflect.KFunction1

@Composable
fun SportView(sport: Sport, isViewExpanded : Boolean? , isFavoriteFilterActivated : Boolean? , onEvent: KFunction1<UiEvent, Unit>) {

    Column(modifier = Modifier.fillMaxWidth()) {
        SportRow(
            icon = sport.sportIcon,
            sportLabel = sport.sportName ?: "",
            isFavoriteFilterActive = isFavoriteFilterActivated ?: false,
            isViewExpanded = isViewExpanded ?: true,
            onEvent = onEvent,
            sportId = sport.sportId ?: ""

        )
        AnimatedVisibility(
            visible = isViewExpanded ?: true,
            enter = expandVertically(
                expandFrom = Alignment.Bottom
            ) + fadeIn(),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Bottom
            ) + fadeOut()
        ) {
            GamesView(firstRow = sport.liveGameFirstRow, secondRow = sport.liveGameSecondRow ,onEvent=onEvent)
        }
    }
}