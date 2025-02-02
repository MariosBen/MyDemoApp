package com.benetatos.livepage_data.remote.dto

import com.google.gson.annotations.SerializedName

data class ActiveEventDto(
    @SerializedName("i") val eventId: String?,
    @SerializedName("si") val sportId: String?,
    @SerializedName("d") val eventName: String?,
    @SerializedName("tt") val eventStartTime: Long?,
)