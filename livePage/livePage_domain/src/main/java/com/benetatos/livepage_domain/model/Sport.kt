package com.benetatos.livepage_domain.model

data class Sport(
    val sportIcon: Int?,
    val sportName: String?,
    val sportId: String?,
    val liveGameFirstRow : List<LiveGame>,
    val liveGameSecondRow : List<LiveGame>
)
