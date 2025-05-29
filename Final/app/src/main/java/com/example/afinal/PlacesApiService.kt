package com.example.afinal

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

// Google Places API response data model
// Only partial fields are defined for simplicity

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

// Retrofit interface
interface PlacesApiService {
    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyTransitStations(
        @Query("location") location: String, // "lat,lng"
        @Query("radius") radius: Int = 1000, // unit: meters
        @Query("type") type: String = "transit_station",
        @Query("key") apiKey: String = "AIzaSyAFNT_hba3ms-n8sjwv2cEThHhO-z6Z_Cs"
    ): Call<PlacesResponse>
} 