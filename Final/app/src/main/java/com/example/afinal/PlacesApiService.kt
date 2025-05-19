package com.example.afinal

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

// Google Places API响应数据模型
// 这里只定义部分字段，简化演示

data class PlacesResponse(
    val results: List<PlaceResult>
)

data class PlaceResult(
    val name: String,
    val geometry: Geometry
)

data class Geometry(
    val location: Location
)

data class Location(
    val lat: Double,
    val lng: Double
)

// Retrofit接口
interface PlacesApiService {
    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyTransitStations(
        @Query("location") location: String, // "lat,lng"
        @Query("radius") radius: Int = 1000, // 单位：米
        @Query("type") type: String = "transit_station",
        @Query("key") apiKey: String = "AIzaSyAFNT_hba3ms-n8sjwv2cEThHhO-z6Z_Cs"
    ): Call<PlacesResponse>
} 