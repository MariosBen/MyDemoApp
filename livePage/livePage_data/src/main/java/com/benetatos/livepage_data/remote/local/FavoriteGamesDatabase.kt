package com.benetatos.livepage_data.remote.local


import androidx.room.Database
import androidx.room.RoomDatabase
import com.benetatos.livepage_data.remote.local.entity.FavoriteGameEntity

@Database(
    entities = [FavoriteGameEntity::class],
    version = 1
)
abstract class FavoriteGamesDatabase : RoomDatabase(){

    abstract val dao : FavoriteGamesDao
}