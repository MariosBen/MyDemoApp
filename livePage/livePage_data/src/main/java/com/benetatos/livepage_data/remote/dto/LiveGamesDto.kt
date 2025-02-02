package com.benetatos.livepage_data.remote.dto

import com.google.gson.annotations.SerializedName

data class LiveGamesDto (
    @SerializedName("i") val sportId : String?,
    @SerializedName("d") val sportName : String?,
    @SerializedName("e") val activeEvent : List<ActiveEventDto>?,
)