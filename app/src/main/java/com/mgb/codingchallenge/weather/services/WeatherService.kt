package com.mgb.codingchallenge.weather.services

import com.mgb.codingchallenge.weather.models.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getWeatherForLocation(
        @Query("appid") appid: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "imperial"
    ): Call<WeatherResponse>
}