package com.benetatos.livepage_data.remote.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteGameEntity(
    val favoriteGameId : String,
    val sportId : String,
    @PrimaryKey val id: Int? = null
)