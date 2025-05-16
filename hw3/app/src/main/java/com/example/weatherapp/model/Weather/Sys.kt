package com.example.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName


//TODO
// Create data class Sys (Refer to API Response)
// Hint: Refer to Wind Data Class

data class Sys(
    @SerializedName("country") val country: String = "",
    @SerializedName("sunrise") val sunrise: Long = 0,
    @SerializedName("sunset") val sunset: Long = 0
)
