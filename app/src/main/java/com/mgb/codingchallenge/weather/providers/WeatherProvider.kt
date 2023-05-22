package com.mgb.codingchallenge.weather.providers

import android.util.Log
import com.mgb.codingchallenge.weather.models.GeocodingResponse
import com.mgb.codingchallenge.weather.models.WeatherResponse
import com.mgb.codingchallenge.weather.services.GeocodingService
import com.mgb.codingchallenge.weather.services.WeatherService
import retrofit2.HttpException
import retrofit2.await
import javax.inject.Inject

class WeatherProvider @Inject constructor(
    private val weatherService: WeatherService,
    private val geocodingService: GeocodingService
) {
    companion object {
        private val APP_ID = "21e14911c25690550e971ef6c788f5b9"
    }

    suspend fun getWeatherForLocation(lat: Double, lon: Double): WeatherResponse =
        weatherService.getWeatherForLocation(APP_ID, lat, lon).await()

    suspend fun getWeatherForLocation(
        location: String,
        fallbackLat: Double = 0.0,
        fallbackLon: Double = 0.0
    ): WeatherResponse {
        var geocodingResponse: GeocodingResponse? = null

        try {
            geocodingResponse =
                geocodingService.getLatLonForCity(APP_ID, location).await().firstOrNull()
        } catch (ex: HttpException) {
            // Can handle errors here
            Log.d("getWeatherForLocation", ex.message())
        }

        return weatherService.getWeatherForLocation(
            APP_ID,
            geocodingResponse?.lat ?: fallbackLat,
            geocodingResponse?.lon ?: fallbackLon
        ).await()
    }
}