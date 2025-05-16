package com.example.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName

//TODO
// Create data class Coord (Refer to API Response)
// Hint: Refer to Wind Data Class

data class Coord(
    @SerializedName("lon") val lon: Double = 0.0,
    @SerializedName("lat") val lat: Double = 0.0
)