package com.benetatos.livepage_data.remote.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.benetatos.livepage_data.remote.local.entity.FavoriteGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteGamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteGame(favoriteGameEntity: FavoriteGameEntity)

    @Query("DELETE FROM FavoriteGameEntity WHERE favoriteGameId = :favoriteGameId AND sportId = :sportId")
    suspend fun deleteFavoriteGame(favoriteGameId: String, sportId: String)

    @Query("SELECT * FROM FAVORITEGAMEENTITY")
    fun getFavoriteGames () : Flow<List<FavoriteGameEntity>>
}