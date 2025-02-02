package com.benetatos.livepage_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.benetatos.core.util.UiEvent
import com.benetatos.core_ui.LocalSpacing
import com.benetatos.core_ui.blue
import com.benetatos.core_ui.grey
import com.benetatos.core_ui.red
import com.benetatos.core_ui.white
import com.benetatos.livepage_domain.model.LiveGame
import kotlin.reflect.KFunction1

@Composable
fun GamesView(
    firstRow: List<LiveGame>,
    secondRow: List<LiveGame>,
    onEvent: KFunction1<UiEvent, Unit>
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Gray)
    ) {
        val itemWidth by remember { mutableStateOf(maxWidth / 4) }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = grey),
        ) {
            items(firstRow.size) { int ->

                Column(
                    modifier = Modifier
                        .width(itemWidth)
                        .padding(vertical = LocalSpacing.current.spaceLarge).testTag("rowItem$int")
                ) {
                    firstRow?.getOrNull(int)?.let {
                        GameItem(liveGame = it, onEvent = onEvent)
                    }

                    secondRow?.getOrNull(int)?.let {
                        GameItem(liveGame = it, onEvent = onEvent)
                    }

                }
            }
        }

    }

}

@Composable
fun GameItem(liveGame: LiveGame,onEvent: KFunction1<UiEvent, Unit>) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.spaceSmall),
    ) {

        Text(
            modifier = Modifier
                .border(1.dp, color = blue, shape = RoundedCornerShape(4.dp))
                .padding(
                    LocalSpacing.current.spaceExtraSmall
                ),
            text = liveGame.timeRemainingFormated ?: "",
            color = white,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )
        Icon(
            modifier = Modifier
                .padding(LocalSpacing.current.spaceMedium)
                .size(24.dp)
                .clickable {
                    onEvent(
                        UiEvent.FavoriteUnFavoriteGame(
                            isFavorite = liveGame.isFavoriteGame == true,
                            gameId= liveGame.gameId ?: "",
                            sportId = liveGame.sportId ?: ""
                        )
                    )
                }.testTag("favoriteIcon$liveGame.gameId"),
            painter = painterResource(if (liveGame.isFavoriteGame == true) com.benetatos.core.R.drawable.ic_favorite_filled else com.benetatos.core.R.drawable.ic_favorite_unfilled),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Text(
            text = liveGame.teamHome ?: "",
            color = white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        VsView()
        Text(
            text = liveGame.teamAway ?: "",
            color = white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun VsView() {
    Text(text = "vs", color = red)
}