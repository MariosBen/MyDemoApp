package com.benetatos.livepage_domain.model

data class LiveGame(
    val timeRemaining: Long?,
    val isFavoriteGame: Boolean? = null,
    val teamHome: String?= null,
    val teamAway: String? = null,
    val gameId: String? = null,
    val sportId: String? = null,
    val timeRemainingFormated: String? = null
)