package com.example.weatherapp.model.Weather

import com.google.gson.annotations.SerializedName

// 当前天气响应数据类
// 只保留本项目需要用到的字段

data class CurrentWeatherResponse(
    @SerializedName("coord") val coord: Coord?,
    @SerializedName("weather") val weather: List<WeatherItem>?,
    @SerializedName("main") val main: Main?,
    @SerializedName("wind") val wind: Wind?,
    @SerializedName("sys") val sys: Sys?,
    @SerializedName("name") val name: String?,
    @SerializedName("dt") val dt: Long?,
    @SerializedName("cod") val cod: Int?
)

//TODO
// Create data class CurrentWeatherResponse (Refer to API Response)
// Hint: Refer to Wind Data Class