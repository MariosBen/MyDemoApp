package com.benetatos.livepage_data.remote

import com.benetatos.livepage_data.remote.dto.LiveGamesDto
import retrofit2.http.GET

interface LiveGamesApi {

    @GET("/MockSports/sports.json")
    suspend fun getLiveGames() : List<LiveGamesDto>

    companion object{
        const val BASE_URL="https://ios-kaizen.github.io/"
    }
}