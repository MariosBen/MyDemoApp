package com.benetatos.livepage_data.remote.mapper

import com.benetatos.core.util.toFormatTime
import com.benetatos.livepage_data.remote.dto.ActiveEventDto
import com.benetatos.livepage_data.remote.dto.LiveGamesDto
import com.benetatos.livepage_domain.model.LiveGame
import com.benetatos.livepage_domain.model.Sport

fun List<LiveGamesDto>.toLiveGamesList() : List<Sport> {
  return this.map {
      val sportsChunked = it.activeEvent?.chunked(4)
      val firstRowList = sportsChunked?.filterIndexed { index, _ -> index % 2 == 0 }?.flatten()
      val secondRowList = sportsChunked?.filterIndexed { index, _ -> index % 2 != 0 }?.flatten()
        Sport(
            sportName= it.sportName,
            sportId = it.sportId,
            sportIcon = it.sportId?.iconMapper(),
            liveGameFirstRow = firstRowList?.toLiveGameList() ?: emptyList(),
            liveGameSecondRow = secondRowList?.toLiveGameList() ?: emptyList(),
        )
    }
}





fun List<ActiveEventDto>.toLiveGameList(): List<LiveGame> {
    return this.map {
        LiveGame(
            timeRemaining = it.eventStartTime,
            timeRemainingFormated = it.eventStartTime.toFormatTime(),
            isFavoriteGame = false,
            teamHome = it.eventName?.split("-")?.firstOrNull(),
            teamAway = it.eventName?.split("-")?.lastOrNull(),
            gameId = it.eventId,
            sportId = it.sportId
        )

    }
}

fun String.iconMapper() : Int? {
    return when(this){
        "FOOT" -> com.benetatos.core.R.drawable.ic_soccer
        "BASK" -> com.benetatos.core.R.drawable.ic_basket
        "TENN" -> com.benetatos.core.R.drawable.ic_tennis
        "TABL" -> com.benetatos.core.R.drawable.ic_table_tennis
        "VOLL" -> com.benetatos.core.R.drawable.ic_voley
        "ESPS" -> com.benetatos.core.R.drawable.ic_esports
        "ICEH" -> com.benetatos.core.R.drawable.ic_ice_hockey
        "SNOO" -> com.benetatos.core.R.drawable.ic_snooker
        "FUTS" -> com.benetatos.core.R.drawable.ic_futsal
        "DART" -> com.benetatos.core.R.drawable.ic_darts
        else -> null
    }
}