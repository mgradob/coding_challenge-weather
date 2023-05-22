package com.mgb.codingchallenge.weather.services

import com.mgb.codingchallenge.weather.models.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("geo/1.0/direct")
    fun getLatLonForCity(
        @Query("appid") appid: String,
        @Query("q") q: String,
        @Query("limit") limit: Int = 3
    ): Call<List<GeocodingResponse>>
}
